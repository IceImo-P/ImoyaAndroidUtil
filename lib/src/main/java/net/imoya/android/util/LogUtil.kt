package net.imoya.android.util

import net.imoya.android.log.LogUtil as NewLogUtil

/**
 * ログユーティリティ
 */
@Suppress("unused")
object LogUtil {

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(BooleanArray) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: BooleanArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(IntArray) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: IntArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(LongArray) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: LongArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(ShortArray) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: ShortArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(ByteArray) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: ByteArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(FloatArray) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: FloatArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(DoubleArray) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: DoubleArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(CharArray) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: CharArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(Any?) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(obj)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(obj: Any?): String = NewLogUtil.logString(obj)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(Array<*>) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(obj)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: Array<*>): String = NewLogUtil.logString(array)
}