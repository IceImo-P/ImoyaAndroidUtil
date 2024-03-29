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

package net.imoya.android.util.sample

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BundleCompat
import com.google.android.material.snackbar.Snackbar
import net.imoya.android.log.Log
import net.imoya.android.util.ExternalFileLoader
import net.imoya.android.util.FileUtil
import java.io.File

/**
 * ファイル選択画面
 *
 * ファイルマネージャーアプリを起動し、ユーザーが選択したファイルを一時ファイルへインポート(コピー)します。
 *
 * @author IceImo-P
 */
class FileBrowserActivity : AppCompatActivity() {
    /**
     * 定数定義:状態
     */
    private enum class State {
        /**
         * 開始(初期状態)
         */
        START,

        /**
         * エラーメッセージダイアログ表示中
         */
        DISPLAYING_ERROR_MESSAGE,

        /**
         * 外部アプリによるファイル選択待ち
         */
        WAIT_FOR_EXTERNAL_APP,

        /**
         * 外部アプリよりファイル取得開始
         */
        START_LOADING_EXTERNAL_FILE,

        /**
         * 外部アプリよりファイル取得中
         */
        LOADING_EXTERNAL_FILE
    }

    /**
     * Screen status
     */
    private lateinit var state: State

    /**
     * Launcher for file manager app
     */
    private lateinit var fileManagerLauncher: ActivityResultLauncher<Intent>

    /**
     * File uri selected by file manager app
     */
    private var externalUri: Uri? = null

    /**
     * [ExternalFileLoader]
     */
    private var externalFileLoader: ExternalFileLoader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setResult(RESULT_CANCELED)
        this.setContentView(R.layout.file_browser)
        val it = this.intent
        this.title = it.getStringExtra(EXTRA_KEY_TITLE)

        fileManagerLauncher = this.registerForActivityResult(
            StartActivityForResult()
        ) { result: ActivityResult -> onFileManagerResult(result.resultCode, result.data) }

        if (savedInstanceState == null) {
            state = State.START
        } else {
            externalUri = BundleCompat.getParcelable(savedInstanceState, STATE_EXTERNAL_URI, Uri::class.java)
            state = BundleCompat.getParcelable(savedInstanceState, STATE, State::class.java) as State
            if (state == State.LOADING_EXTERNAL_FILE) {
                // 外部ファイル取得中に再起動した場合は、再取得を試みる
                state = State.START_LOADING_EXTERNAL_FILE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(STATE, state)
        outState.putParcelable(STATE_EXTERNAL_URI, externalUri)
    }

    override fun onDestroy() {
        super.onDestroy()
        val efl = externalFileLoader
        if (efl != null) {
            try {
                efl.requestAbort()
            } catch (e: Exception) {
                Log.v(TAG, "onDestroy: Exception", e)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        when (state) {
            State.START ->
                // ファイルマネージャ系アプリ(Storage Access Framework対応アプリ)を起動する
                startFileManagerApp()
            State.START_LOADING_EXTERNAL_FILE -> loadExternalFile()
            else -> {}
        }
    }

    /**
     * Launch a file manager application that supports the Storage Access Framework.
     */
    private fun startFileManagerApp() {
        state = State.WAIT_FOR_EXTERNAL_APP
        val fileManagerIntent = Intent(Intent.ACTION_GET_CONTENT)
        fileManagerIntent.type = intent.type
        fileManagerIntent.addCategory(Intent.CATEGORY_OPENABLE)
        fileManagerIntent.putExtra(
            Intent.EXTRA_MIME_TYPES,
            intent.getStringArrayExtra(Intent.EXTRA_MIME_TYPES)
        )
        try {
//            this.startActivityForResult(intent, REQUEST_CODE_EXTERNAL_APP);
            fileManagerLauncher.launch(fileManagerIntent)
        } catch (e: ActivityNotFoundException) {
            Log.v(TAG, e)

            // Launch message dialog if file manager app is not installed
            state = State.DISPLAYING_ERROR_MESSAGE
            Snackbar.make(
                findViewById(R.id.root),
                R.string.file_browser_external_app_not_found,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun onFileManagerResult(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && data != null) {
            state = State.START_LOADING_EXTERNAL_FILE
            externalUri = data.data
        } else {
            finish()
        }
    }

    private fun loadExternalFile() {
        state = State.LOADING_EXTERNAL_FILE

        val extUri = externalUri
        if (extUri != null) {
            try {
                // 一時ファイルを準備する
                val file = File(this.filesDir, TMP_FILE_NAME)
                if (!FileUtil.setupFile(file)) {
                    // 準備に失敗した場合は、その旨をダイアログで表示する
                    showExternalFileLoadingError()
                    return
                }

                // 指定されたファイルを、一時ファイルへコピーする
                val fileLoader = ExternalFileLoader(this.applicationContext)
                externalFileLoader = fileLoader
                fileLoader.setListener(object : ExternalFileLoader.Listener {
                    override fun onComplete(
                        loader: ExternalFileLoader, param: ExternalFileLoader.Param
                    ) {
                        // 成功した場合は、ファイルの情報を呼び出し元へ返す
                        val owner = this@FileBrowserActivity
                        val data = Intent()
                        data.putExtra(EXTRA_KEY_SELECTED_PATH, param.pathForSave)
                        data.putExtra(EXTRA_KEY_FILE_NAME, loader.sourceFileName)
                        owner.setResult(RESULT_OK, data)
                        owner.finish()
                    }

                    override fun onError(
                        loader: ExternalFileLoader, param: ExternalFileLoader.Param?
                    ) {
                        // 失敗した場合は、その旨をダイアログで表示する
                        showExternalFileLoadingError()
                    }
                })
                fileLoader.execute(
                    ExternalFileLoader.Param(extUri, file.absolutePath)
                )
            } catch (e: java.lang.Exception) {
                Log.e(TAG, e)
                // 失敗した場合は、その旨をダイアログで表示する
                showExternalFileLoadingError()
            }
        }
    }

    private fun showExternalFileLoadingError() {
        state = State.DISPLAYING_ERROR_MESSAGE
        Snackbar.make(
            findViewById(R.id.root),
            R.string.file_browser_failed_to_load_external_file,
            Snackbar.LENGTH_LONG
        ).show()
    }

    companion object {
        /**
         * Extra key:(in)画面タイトル
         */
        const val EXTRA_KEY_TITLE = "Title"

        /**
         * Extra key:(out)一時ファイルのパス
         *
         * ユーザーが選択したファイルの内容をコピーした一時ファイルのパスを返します。
         *
         * 呼び出し元の画面は一時ファイルを恒久的な保存先へ移動するか、処理の終了時に削除する責任を持ちます。
         */
        const val EXTRA_KEY_SELECTED_PATH = "SelectedPath"

        /**
         * Extra key:(out)file name
         */
        const val EXTRA_KEY_FILE_NAME = "FileName"

        /**
         * 再起動時保存データ用キー:現在の状態
         */
        private const val STATE = "State"

        /**
         * 再起動時保存データ用キー:選択されたファイルの URI
         */
        private const val STATE_EXTERNAL_URI = "ExternalUri"

        /**
         * Temporary file name
         */
        private const val TMP_FILE_NAME = "FileBrowserExternalTmp"

        /**
         * Tag for logging
         */
        private const val TAG = "FileBrowserScr"
    }
}