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

import androidx.appcompat.app.AppCompatActivity

/**
 * 実行時権限取得コントローラ
 */
@Suppress("unused")
abstract class PermissionRequestController(
    private val activity: AppCompatActivity,
    private val permissions: Array<String>,
    private val requestCode: Int
) {
    private var inRequest = false

    fun checkAndRequest(): Boolean {
        // 権限取得済みか?
        return if (PermissionUtil.isGrantedAll(activity, permissions)) {
            // 取得済みの場合は、その旨返す
            true
        } else {
            // 取得中か?
            if (!inRequest) {
                // 未取得且つ取得中でない場合は、取得を開始する
                inRequest = true
                PermissionUtil.requestPermissions(activity, requestCode, permissions)
            }
            false
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        @Suppress("UNUSED_PARAMETER") permissions: Array<String>,
        grantResults: IntArray
    ): Boolean {
        // 権限取得処理の結果か?
        if (requestCode != this.requestCode) {
            // 異なる場合は、何もしない
            return false
        }

        // 権限取得したか?
        if (PermissionUtil.isGrantedAll(grantResults)) {
            // 取得した場合は、取得時の処理を行う
            onGrantedAll()
        } else {
            // 拒否された場合は、拒否時の処理を行う
            onDenied(!PermissionUtil.shouldShowRequestPermissionRationale(activity))
        }

        // 権限取得処理中ではなくなった
        inRequest = false
        return true
    }

    /**
     * 権限取得時の処理を実装してください。
     */
    protected abstract fun onGrantedAll()

    /**
     * 権限取得拒否時の処理を実装してください。
     *
     * @param permanently 永続的に拒否(次回から表示しないにチェックし拒否)された場合は true,
     *                    その他の場合は false
     */
    protected abstract fun onDenied(permanently: Boolean)
}