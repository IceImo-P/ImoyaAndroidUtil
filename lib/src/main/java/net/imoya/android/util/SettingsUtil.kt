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

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import java.lang.Exception

/**
 * 端末設定関連ユーティリティ
 */
@Suppress("unused")
object SettingsUtil {
    /**
     * 端末のアプリ設定画面を開きます。
     *
     * @param activity 遷移元画面の [Activity]
     * @return true if succeeded, otherwise false
     */
    @JvmStatic
    fun startApplicationSettings(activity: Activity): Boolean {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", activity.packageName, null)
        return try {
            activity.startActivity(intent)
            true
        } catch (e: Exception) {
            // 端末メーカーがAndroid OSを改変する等して、アプリ設定画面を通常の方法で
            // 開けない環境の場合は、ここへ来る
            UtilLog.w(TAG, "startApplicationSettings: PermissionDenied", e)
            false
        }
    }

    /**
     * Tag for log
     */
    const val TAG = "SettingsUtil"
}