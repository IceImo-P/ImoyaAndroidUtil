package net.imoya.android.util

/**
 * ログユーティリティ
 */
@Suppress("unused")
object LogUtil {
    @JvmStatic
    fun logString(array: BooleanArray?): String {
        return array?.joinToString(prefix = "[", postfix = "]") {
            if (it) "1" else "0"
        }
            ?: "(null)"
    }
}