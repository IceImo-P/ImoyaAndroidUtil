/*
 * Copyright (C) 2022 IceImo-P
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.imoya.android.util

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.provider.OpenableColumns
import java.io.*
import java.lang.ref.WeakReference

/**
 * 外部ファイルローダ
 *
 * Storage Access Framework を用いて外部アプリケーションより取得したファイルの内容を、
 * 任意のパスで指定したファイルへ保存します。
 *
 * @author IceImo-P
 */
@Suppress("unused")
class ExternalFileLoader(context: Context) :
    AsyncTask<ExternalFileLoader.Param, Int, ExternalFileLoader.Result>() {
    /**
     * 入力パラメータモデル
     *
     * @param sourceUri   読み取り元 [Uri]
     * @param pathForSave 保存先ファイルのパス
     */
    class Param(
        val sourceUri: Uri,
        val pathForSave: String
    )

    /**
     * 結果モデル
     *
     * @param param 入力パラメータ
     * @param success 成功フラグ
     */
    class Result(val param: Param?, val success: Boolean)

    /**
     * 結果リスナ
     */
    interface Listener {
        /**
         * 保存完了時に呼び出されます。
         *
         * @param loader 呼び出し元
         * @param param  入力パラメータ
         */
        fun onComplete(loader: ExternalFileLoader, param: Param)

        /**
         * 保存失敗時に呼び出されます。
         *
         * @param loader 呼び出し元
         * @param param  入力パラメータ
         */
        fun onError(loader: ExternalFileLoader, param: Param?)
    }

    /**
     * Buffer size (in bytes)
     */
    @Suppress("weaker")
    var bufferSize: Int = 32768

    /**
     * 読み取ったファイルのファイル名
     */
    @Suppress("weaker")
    var sourceFileName: String? = null
        private set

    /**
     * [Context]
     */
    private val context: WeakReference<Context>

    /**
     * 結果リスナ
     */
    private var listener: Listener? = null

    /**
     * 中断フラグ
     */
    private var abortRequested = false

    init {
        this.context = WeakReference(context)
    }

    /**
     * 結果リスナを設定します。
     *
     * @param listener 結果リスナ、又はリスナを使用しないことを指定するnull
     */
    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    /**
     * 実行中であれば、処理の中断を要求します。
     *
     *  * 未実行の場合は、実行開始後に直ちに中断されます。
     *  * 中断された場合、結果リスナは呼び出されません。
     */
    fun requestAbort() {
        abortRequested = true
    }

    override fun doInBackground(vararg params: Param?): Result? {
        val param = if (params.isNotEmpty()) params[0] else null
        if (param == null) {
            UtilLog.v(TAG, "ERROR: param == null")
            return Result(null, false)
        } else {
            if (abortRequested) {
                UtilLog.v(TAG, "Abort")
                return Result(param, false)
            }
            return try {
                sourceFileName = getSourceTitle(param.sourceUri)
                if (abortRequested) {
                    UtilLog.v(TAG, "Abort")
                    Result(param, false)
                }
                copyFile(param)
            } catch (e: Exception) {
                UtilLog.d(TAG, "doInBackground: Error", e)
                Result(param, false)
            }
        }
    }

    /**
     * Returns title of source content.
     *
     * @param uri URI of source content
     * @return Title string or null
     */
    private fun getSourceTitle(uri: Uri): String? {
        val cursor = context.get()?.contentResolver?.query(
            uri, null, null, null, null, null
        ) ?: return null
        return cursor.use { c ->
            if (c.moveToFirst()) {
                c.getString(
                    c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                )
            } else {
                null
            }
        }
    }

    private fun copyFile(param: Param): Result {
        val sourceStream: InputStream = getInputStream(param)
            ?: return Result(param, false)
        sourceStream.use { inStream ->
            val destStream: OutputStream = getOutputStream(param)
                ?: return Result(param, false)
            destStream.use { outStream ->
                copyFileCore(inStream, outStream)
            }
        }
        if (abortRequested) {
            UtilLog.v(TAG, "Abort")
            return Result(param, false)
        }
        return Result(param, true)
    }

    private fun copyFileCore(source: InputStream, dest: OutputStream) {
        val buffer = ByteArray(bufferSize)
        while (true) {
            val read = source.read(buffer)
            if (read == -1) {
                break
            }
            if (abortRequested) {
                UtilLog.v(TAG, "Abort")
                break
            }
            dest.write(buffer, 0, read)
        }
    }

    /**
     * Returns [InputStream] of source content.
     *
     * @return [InputStream], or null when failed
     */
    private fun getInputStream(param: Param): InputStream? {
        return try {
            val bareStream = context.get()?.contentResolver?.openInputStream(param.sourceUri)
            if (bareStream != null) BufferedInputStream(bareStream) else null
        } catch (e: FileNotFoundException) {
            UtilLog.d(TAG, "Failed to open source content", e)
            null
        }
    }

    /**
     * Returns [OutputStream] of destination file.
     *
     * @return [OutputStream], or null when failed
     */
    private fun getOutputStream(param: Param): OutputStream? {
        return try {
            val file = getFile(param)
            if (file != null) FileOutputStream(file) else null
        } catch (e: java.lang.Exception) {
            UtilLog.d(TAG, "Failed to open destination file", e)
            null
        }
    }

    /**
     * 保存先のファイルを準備します。
     *
     *  * 指定のパスにファイルが存在しない場合は、空のファイルを作成します。
     *  * 指定のパスにファイルが存在する場合は、何もしません。
     *  * 指定のパスが通常のファイルではない(ディレクトリ等である)場合は null を返します。
     *
     * @return 保存先の [File], または null
     */
    private fun getFile(param: Param): File? {
        val file = File(param.pathForSave)
        return if (FileUtil.setupFile(file)) file else null
    }

    override fun onPostExecute(result: Result) {
        try {
            if (!abortRequested) {
                if (result.success && result.param != null) {
                    listener?.onComplete(this, result.param)
                } else {
                    listener?.onError(this, result.param)
                }
            }
        } catch (e: Exception) {
            UtilLog.d(TAG, "onPostExecute: Error at Listener", e)
        }
    }

    companion object {
        /**
         * Tag for log
         */
        private const val TAG = "ExternalFileLoader"
    }
}