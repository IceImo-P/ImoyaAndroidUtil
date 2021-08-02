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
            Log.w("PermissionDenied", e)
            false
        }
    }
}