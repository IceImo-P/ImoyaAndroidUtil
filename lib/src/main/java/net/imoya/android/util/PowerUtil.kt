package net.imoya.android.util

import android.annotation.TargetApi
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
     * Partial [PowerManager.WakeLock] を取得します。
     * @param context [Context]
     * @param name 名称
     * @return [PowerManager.WakeLock]
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
    fun isInteractiveKitkatWatch(context: Context): Boolean {
        val manager: PowerManager = context.getSystemService(POWER_SERVICE) as PowerManager
        return manager.isInteractive
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Suppress("deprecation")
    fun isInteractiveLegacy(context: Context): Boolean {
        val manager: PowerManager = context.getSystemService(POWER_SERVICE) as PowerManager
        return manager.isScreenOn
    }
}