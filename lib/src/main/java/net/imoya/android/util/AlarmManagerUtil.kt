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
import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings

/**
 * Utility methods for operate [AlarmManager]
 *
 * @author IceImo-P
 */
@Suppress("unused")
object AlarmManagerUtil {
    /**
     * Returns [AlarmManager.canScheduleExactAlarms] if supported.
     *
     * @param alarmManager [AlarmManager]
     * @return [AlarmManager.canScheduleExactAlarms] value if supported OS version, otherwise true.
     */
    @JvmStatic
    fun canScheduleExactAlarms(alarmManager: AlarmManager): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    /**
     * Start Android system settings activity for setting exact alarm permission.
     *
     * @param context [Context]
     */
    @JvmStatic
    fun startExactAlarmsSettingsActivity(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }
    }

    /**
     * 次回の起動を予約します。
     * Android6.0以上でデバイスがスリープ中の場合、正確な時刻に起動しない場合があります。
     *
     * @param alarmManager    [AlarmManager]
     * @param type            時刻指定種別
     * @param triggerAtMillis 時刻指定
     * @param operation       指定時刻にBroadcastするIntent指定
     */
    @JvmStatic
    fun setExact(
        alarmManager: AlarmManager, type: Int, triggerAtMillis: Long, operation: PendingIntent
    ) {
        setExactMarshmallow(alarmManager, type, triggerAtMillis, operation)
    }

    /**
     * 次回の起動を予約します。
     * Android6.0以上でデバイスがスリープ中の場合であっても正確に起動しますが、
     * バッテリーを消耗します。
     *
     * SCHEDULE_EXACT_ALARM Permission requires if application targets Android 12+ (API level 31+)
     *
     * @param alarmManager    [AlarmManager]
     * @param triggerAtMillis 時刻指定
     * @param operation       指定時刻にBroadcastする [PendingIntent]
     * @param showIntent      他のアプリより呼び出し可能な、設定画面起動 [PendingIntent]
     */
    @JvmStatic
    fun setAlarmClock(
        alarmManager: AlarmManager, triggerAtMillis: Long, operation: PendingIntent,
        showIntent: PendingIntent
    ) {
        setAlarmClockLollipop(
            alarmManager, triggerAtMillis, operation, showIntent
        )
    }

//    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("MissingPermission")
    @JvmStatic
    private fun setExactKitkat(
        am: AlarmManager, type: Int, triggerAtMillis: Long,
        operation: PendingIntent
    ) {
        UtilLog.v(TAG, "setExactKitkat")
        // Android 4.4 以上 6.0 未満では、setExactメソッドを使用する
        am.setExact(type, triggerAtMillis, operation)
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    @JvmStatic
    private fun setExactMarshmallow(
        am: AlarmManager, type: Int, triggerAtMillis: Long,
        operation: PendingIntent
    ) {
        UtilLog.v(TAG, "setExactMarshmallow")
        // Android 6.0 以上では、setExactAndAllowWhileIdleメソッドを使用する
//        // 2015-09-26 v1.2.1 一時的に6.0サポートをキャンセルするため、setExactへ変更
//        am.setExact(type, triggerAtMillis, operation);
        // 2016-08-14 Android6.0サポート再開
        am.setExactAndAllowWhileIdle(type, triggerAtMillis, operation)
    }

    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    private fun setAlarmClockLollipop(
        am: AlarmManager, triggerAtMillis: Long, operation: PendingIntent,
        showIntent: PendingIntent
    ) {
        UtilLog.v(TAG, "setAlarmClockLollipop")
        // Android 6.0 以上では、setAlarmClockメソッドを使用する
        // Android 12 以上では SCHEDULE_EXACT_ALARM Permission が必要
        val info = AlarmClockInfo(triggerAtMillis, showIntent)
        am.setAlarmClock(info, operation)
    }

    /**
     * Tag for log
     */
    const val TAG = "AlarmManagerUtil"
}