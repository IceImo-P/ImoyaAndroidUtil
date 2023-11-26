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

import android.app.ActivityManager
import android.content.Context
import android.content.Context.POWER_SERVICE
import android.os.Build
import android.os.PowerManager.WakeLock
import android.os.PowerManager
import androidx.annotation.RequiresApi

/**
 * 電源関連ユーティリティー
 *
 * @author IceImo-P
 */
@Suppress("unused")
object PowerUtil {
    /**
     * Returns Partial [WakeLock].
     *
     * @param context [Context]
     * @param name Name of WakeLock
     * @return [WakeLock]
     */
    @JvmStatic
    fun getPartialWakeLock(
        context: Context, name: String?
    ): WakeLock {
        val manager: PowerManager = context.getSystemService(POWER_SERVICE) as PowerManager
        return manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, name)
    }

    /**
     * 画面が操作可能な状態(通常、画面がONの状態)であるか否かを返します。
     *
     * @param context [Context]
     * @return 画面が操作可能な状態の場合 true, その他の場合 false
     */
    @JvmStatic
    fun isInteractive(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            isInteractiveKitkatWatch(context)
        } else {
            isInteractiveLegacy(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun isInteractiveKitkatWatch(context: Context): Boolean {
        val manager: PowerManager = context.getSystemService(POWER_SERVICE) as PowerManager
        return manager.isInteractive
    }

//    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Suppress("deprecation")
    private fun isInteractiveLegacy(context: Context): Boolean {
        val manager: PowerManager = context.getSystemService(POWER_SERVICE) as PowerManager
        return manager.isScreenOn
    }

    /**
     * バックグラウンド動作が制限されているか否かを返します。
     *
     * @param context [Context]
     * @return バックグラウンド動作が制限されている場合 true, その他の場合 false
     */
    @JvmStatic
    fun isBackgroundRestricted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            // No restriction at prior to Android 9
            false
        } else {
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.isBackgroundRestricted
        }
    }
}