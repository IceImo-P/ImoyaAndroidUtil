package net.imoya.android.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

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
            Log.d(TAG, "Android Version < 6.0")
            return true
        } else {
            // Android 6.0以上の場合は、実行時に権限を取得する必要がある
            Log.d(TAG, "Android Version >= 6.0")

            // 権限を取得済みか?
            var result = true
            for (permission in permissions) {
                if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission = $permission granted = NO")
                    result = false
                    break
                }
                Log.d(TAG, "Permission = $permission granted = YES")
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

    @JvmStatic
    fun isGrantedAll(grantResults: IntArray): Boolean {
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    fun shouldShowRequestPermissionRationale(
        activity: AppCompatActivity
    ): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Android 6.0未満の場合は、インストール時点で権限取得済みである
            Log.d(TAG, "Android Version < 6.0")
            false
        } else {
            // Android 6.0以上の場合は、Activityの機能を呼び出す
            activity.shouldShowRequestPermissionRationale(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }
}