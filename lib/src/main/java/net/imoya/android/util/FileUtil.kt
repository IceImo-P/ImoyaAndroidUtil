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
import android.os.Environment
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.lang.StringBuilder
import java.nio.charset.Charset

/**
 * Utilities for file operation
 *
 * @author IceImo-P
 */
@Suppress("unused")
object FileUtil {
    /**
     * Tag for log
     */
    private const val TAG = "FileUtil"

    /**
     * Returns preference name for default SharedPreferences.
     *
     * @param context [Context]
     * @return Preference name
     */
    @JvmStatic
    fun defaultSharedPreferencesName(context: Context): String {
        return context.packageName + "_preferences"
    }

    /**
     * アプリケーションデフォルトのプリファレンスファイルが存在するか否かを返します。
     *
     * @param context [Context]
     * @return true if default SharedPreferences file exists, otherwise false.
     */
    @JvmStatic
    fun sharedPreferencesIsExist(context: Context): Boolean {
        val prefName = defaultSharedPreferencesName(context)
        val parentPath: String = context.filesDir.parent ?: context.filesDir.absolutePath
        val prefPath = "$parentPath/shared_prefs/$prefName.xml"
        UtilLog.i(TAG) { "prefPath = $prefPath" }
        return File(prefPath).exists()
    }

    /**
     * ファイルの準備処理を行います。
     *  * ファイルが存在しない場合は、空のファイルを作成します。
     *  * ファイルが存在する場合は、何もしません。
     *  * 指定した [File] が通常のファイルではない(ディレクトリ等である)場合は失敗を返します。
     *
     *
     * @param file [File]
     * @return true if success, otherwise false.
     */
    @JvmStatic
    fun setupFile(file: File): Boolean {
        if (file.exists() && !file.isFile) {
            return false
        }
        try {
            if (!file.exists() && !file.createNewFile()) {
                return false
            }
        } catch (e: IOException) {
            UtilLog.w(TAG, e)
            return false
        }
        return true
    }

    /**
     * 文字列が保存されたファイルを読み取り、内容の文字列を返します。
     *
     * @param file        [File]
     * @param charsetName 文字セット名
     * @return 読み取った文字列
     * @throws IOException I/Oエラーが発生しました。
     */
    @Throws(IOException::class)
    @JvmStatic
    fun readFile(file: File, charsetName: String): String {
        val inputStream: InputStream = FileInputStream(file)
        val stream = BufferedInputStream(inputStream)
        val reader = InputStreamReader(
            stream, Charset.forName(charsetName)
        )
        return try {
            val s = StringBuilder()
            val buffer = CharArray(32768)
            while (true) {
                val read = reader.read(buffer)
                if (read == -1) {
                    break
                }
                s.append(buffer, 0, read)
            }
            s.toString()
        } finally {
            try {
                reader.close()
            } catch (e: IOException) {
                UtilLog.d(TAG, "readFile: IOException at closing stream", e)
            }
        }
    }

    /**
     * 指定ファイルへ、指定の文字列を保存します。
     *
     * @param file        [File]
     * @param charsetName 文字セット名
     * @param content     保存する文字列
     * @throws IOException I/Oエラーが発生しました。
     */
    @Throws(IOException::class)
    @JvmStatic
    fun writeFile(
        file: File, charsetName: String, content: String
    ) {
        val outputStream: OutputStream = FileOutputStream(file)
        val stream = BufferedOutputStream(outputStream)
        val writer = OutputStreamWriter(
            stream, Charset.forName(charsetName)
        )
        try {
            writer.write(content)
        } finally {
            try {
                writer.close()
            } catch (e: IOException) {
                UtilLog.d(TAG, "writeFile: IOException at closing stream", e)
            }
        }
    }

    /**
     * 指定ストリームへ、指定の文字列を保存します。
     *
     * @param outputStream [OutputStream]
     * @param charsetName  文字セット名
     * @param content      保存する文字列
     * @throws IOException I/Oエラーが発生しました。
     */
    @Throws(IOException::class)
    @JvmStatic
    fun writeToStream(
        outputStream: OutputStream, charsetName: String, content: String
    ) {
        val stream = BufferedOutputStream(outputStream)
        val writer = OutputStreamWriter(
            stream, Charset.forName(charsetName)
        )
        try {
            writer.write(content)
        } finally {
            try {
                writer.close()
            } catch (e: IOException) {
                UtilLog.d(TAG, "writeFile: IOException at closing stream", e)
            }
        }
    }

    /**
     * InputStreamの内容を全て、指定ファイルに書き出します。
     *
     * @param source InputStream
     * @param dest   書き出し先のファイル
     * @throws IOException I/Oエラーが発生しました。
     */
    @Throws(IOException::class)
    @JvmStatic
    fun saveStreamToFile(source: InputStream, dest: File) {
        saveStreamToFile(source, dest, 32768)
    }

    /**
     * InputStreamの内容を全て、指定ファイルに書き出します。
     *
     * @param source  InputStream
     * @param dest    書き出し先のファイル
     * @param bufSize バッファサイズ(bytes)
     * @throws IOException I/Oエラーが発生しました。
     */
    @Throws(IOException::class)
    @JvmStatic
    fun saveStreamToFile(source: InputStream, dest: File, bufSize: Int) {
        BufferedOutputStream(FileOutputStream(dest)).use { os ->
            val buffer = ByteArray(bufSize)
            var read: Int
            while (true) {
                read = source.read(buffer)
                if (read == -1) break
                os.write(buffer, 0, read)
            }
        }
    }

    /**
     * デフォルトの外部ストレージまたは内部ストレージが、読み取り可能であるか否かを返します。
     *
     * @return true:読み取り可能, false:読み取り不可能(取り外されている等)
     */
    @JvmStatic
    fun primaryExternalStorageIsReadable(): Boolean {
        // 外部ストレージ(または内部ストレージ)の接続状態を取得する
        val mediaState = Environment.getExternalStorageState()

        // 読み取りアクセスまたは書き込みアクセス可能であれば、読み取り可能である
        return mediaState == Environment.MEDIA_MOUNTED || mediaState == Environment.MEDIA_MOUNTED_READ_ONLY
    }
}