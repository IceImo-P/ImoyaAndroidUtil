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

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi

/**
 * [TelephonyManager] 関連ユーティリティ
 */
@Suppress("unused")
object TelephonyUtil {
    /**
     * 電話機能が待機中である(即ち、通話中でも発着信中でもない)か否かを返します。
     *
     * Requires READ_PHONE_STATE permission for Android 12+(API level 31+)
     *
     * @param context [Context]
     * @return 待機中である場合はtrue, それ以外の場合はfalse
     */
    @JvmStatic
    fun isIdle(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= 30/* Build.VERSION_CODES.R */) {
            isIdleR(context)
        } else {
            isIdleLegacy(context)
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    fun isIdleR(context: Context): Boolean {
        val manager = context.getSystemService(
            Context.TELECOM_SERVICE
        ) as TelecomManager
        return !manager.isInCall
    }

    @SuppressLint("deprecation")
    @TargetApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun isIdleLegacy(context: Context): Boolean {
        val manager = context.getSystemService(
            Context.TELEPHONY_SERVICE
        ) as TelephonyManager
        return manager.callState == TelephonyManager.CALL_STATE_IDLE
    }
}