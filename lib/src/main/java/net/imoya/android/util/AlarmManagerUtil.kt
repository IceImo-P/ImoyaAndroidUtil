package net.imoya.android.util

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.os.Build
import net.imoya.android.util.Log.d

/**
 * Utility methods for operate [AlarmManager]
 *
 * @author IceImo-P
 */
@Suppress("unused")
object AlarmManagerUtil {
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @JvmStatic
    private fun setExactKitkat(
        am: AlarmManager, type: Int, triggerAtMillis: Long,
        operation: PendingIntent
    ) {
        d("AlarmManagerUtil", "setExactKitkat")
        // Android 4.4 以上 6.0 未満では、setExactメソッドを使用する
        am.setExact(type, triggerAtMillis, operation)
    }

    @TargetApi(Build.VERSION_CODES.M)
    @JvmStatic
    private fun setExactMarshmallow(
        am: AlarmManager, type: Int, triggerAtMillis: Long,
        operation: PendingIntent
    ) {
        d("AlarmManagerUtil", "setExactMarshmallow")
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
        d("AlarmManagerUtil", "setAlarmClockLollipop")
        // Android 6.0 以上では、setAlarmClockメソッドを使用する
        // Android 12 以上では SCHEDULE_EXACT_ALARM Permission が必要
        val info = AlarmClockInfo(triggerAtMillis, showIntent)
        am.setAlarmClock(info, operation)
    }
}