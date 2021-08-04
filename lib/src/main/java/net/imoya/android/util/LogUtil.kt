package net.imoya.android.util

/**
 * ログユーティリティ
 */
@Suppress("unused")
object LogUtil {
    @JvmStatic
    fun logString(array: BooleanArray): String {
        return array.joinToString(prefix = "[", postfix = "]") {
            if (it) "1" else "0"
        }
    }

    @JvmStatic
    fun logString(array: IntArray): String {
        return array.joinToString(prefix = "[", postfix = "]") {
            it.toString()
        }
    }

    @JvmStatic
    fun logString(array: LongArray): String {
        return array.joinToString(prefix = "[", postfix = "]") {
            it.toString()
        }
    }

    @JvmStatic
    fun logString(array: ShortArray): String {
        return array.joinToString(prefix = "[", postfix = "]") {
            it.toString()
        }
    }

    @JvmStatic
    fun logString(array: ByteArray): String {
        return array.joinToString(prefix = "[", postfix = "]") {
            it.toString()
        }
    }

    @JvmStatic
    fun logString(array: FloatArray): String {
        return array.joinToString(prefix = "[", postfix = "]") {
            it.toString()
        }
    }

    @JvmStatic
    fun logString(array: DoubleArray): String {
        return array.joinToString(prefix = "[", postfix = "]") {
            it.toString()
        }
    }

    @JvmStatic
    fun logString(array: CharArray): String {
        return array.joinToString(prefix = "[", postfix = "]") {
            it.toString()
        }
    }

    @JvmStatic
    fun logString(obj: Any?): String {
        return when (obj) {
            null -> "null"
            is IntArray -> logString(obj)
            is LongArray -> logString(obj)
            is ShortArray -> logString(obj)
            is ByteArray -> logString(obj)
            is FloatArray -> logString(obj)
            is DoubleArray -> logString(obj)
            is CharArray -> logString(obj)
            is BooleanArray -> logString(obj)
            is Array<*> -> logString(obj)
            else -> obj.toString()
        }
    }

    @JvmStatic
    fun logString(array: Array<*>): String = array.contentDeepToString()
}