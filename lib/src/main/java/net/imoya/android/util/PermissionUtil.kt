/*
 * Copyright (C) 2022-2023 IceImo-P
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

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

/**
 * Permission utilities
 *
 * @author IceImo-P
 */
internal object PermissionUtil {
    /**
     * Tag for log
     */
    private const val TAG = "PermissionUtils"

    /**
     * 指定の権限を全て取得済みであるか否かを返します。
     *
     * @param context [Context]
     * @param permissions 権限([Manifest.permission] にて定義される文字列)のリスト
     * @return 取得済みの場合はtrue, 未取得の場合はfalse
     */
    @JvmStatic
    // fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
    fun isGrantedAll(context: Context, permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Android 6.0未満の場合は、インストール時点で権限取得済みである
            UtilLog.v(TAG, "Android Version < 6.0")
            return true
        } else {
            // Android 6.0以上の場合は、実行時に権限を取得する必要がある
            UtilLog.v(TAG, "Android Version >= 6.0")

            // 権限を取得済みか?
            var result = true
            for (permission in permissions) {
                if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    UtilLog.d(TAG) { "Permission = $permission granted = NO" }
                    result = false
                    break
                }
                UtilLog.d(TAG) { "Permission = $permission granted = YES" }
            }
            return result
        }
    }

    /**
     * 指定の実行時権限取得処理を開始します。
     *
     * @param activity [AppCompatActivity]
     * @param requestCode リクエストコード
     * @param permissions 権限([Manifest.permission] にて定義される文字列)のリスト
     */
    @JvmStatic
    fun requestPermissions(
        activity: AppCompatActivity, requestCode: Int, permissions: Array<String>
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permissions, requestCode)
        }
    }

    /**
     * 指定の権限取得結果が、すべて許可されているか否かを返します。
     *
     * @param grantResults 権限取得結果
     * @return すべて許可されている場合は true, その他の場合は false
     */
    @JvmStatic
    fun isGrantedAll(grantResults: IntArray): Boolean {
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 指定の権限はユーザーの手動設定が必要か否かを返します。
     *
     * @param activity [AppCompatActivity]
     * @param permission 権限([Manifest.permission] にて定義される文字列)
     * @return ユーザーの手動設定が必要な場合 true, その他の場合は false
     */
    @JvmStatic
    @Deprecated(
        "Use ActivityCompat.shouldShowRequestPermissionRationale",
        replaceWith = ReplaceWith(
            "ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)"
        )
    )
    fun shouldShowRequestPermissionRationale(
        activity: AppCompatActivity,
        permission: String
    ): Boolean {

        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }
}