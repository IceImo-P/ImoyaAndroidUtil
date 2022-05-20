package net.imoya.android.util

import android.content.Context
import net.imoya.android.log.LogLevel
import net.imoya.android.log.LogUtil
import net.imoya.android.log.LogWrapper
import java.util.*

/**
 * Log
 *
 * @author IceImo-P
 */
@Suppress("unused")
object Log {
    private var logWrapper = LogWrapper(LogLevel.INFO)

    /**
     * Initialize log output
     *
     * @param context [Context]
     */
    @JvmStatic
    fun init(context: Context) {
        logWrapper = LogWrapper(LogLevel.from(context.getString(R.string.imoya_log_level)))
    }

    /**
     * Returns date and time string for log
     *
     * @param time RTC datetime
     * @return String for log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil#dateTimeString(Long) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.dateTimeString(time)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun getDateTimeString(time: Long): String {
        return LogUtil.dateTimeString(time)
    }

    /**
     * Returns date and time string for log
     *
     * @param calendar
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil#logString(Calendar) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(calendar)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun getString(calendar: Calendar): String {
        return LogUtil.logString(calendar)
    }

    @JvmStatic
    fun v(tag: String?, msg: String) {
        logWrapper.v(tag, msg)
    }

    @JvmStatic
    fun v(tag: String?, msgCallback: () -> String) {
        logWrapper.v(tag, msgCallback)
    }

    @JvmStatic
    fun v(tag: String?, msg: String?, tr: Throwable?) {
        logWrapper.v(tag, msg, tr)
    }

    @JvmStatic
    fun v(tag: String?, msgCallback: () -> String, tr: Throwable?) {
        logWrapper.v(tag, msgCallback, tr)
    }

    @JvmStatic
    fun v(tag: String?, tr: Throwable) {
        logWrapper.v(tag, tr)
    }

    @JvmStatic
    fun d(tag: String?, msg: String) {
        logWrapper.d(tag, msg)
    }

    @JvmStatic
    fun d(tag: String?, msgCallback: () -> String) {
        logWrapper.d(tag, msgCallback)
    }

    @JvmStatic
    fun d(tag: String?, msg: String?, tr: Throwable?) {
        logWrapper.d(tag, msg, tr)
    }

    @JvmStatic
    fun d(tag: String?, msgCallback: () -> String, tr: Throwable?) {
        logWrapper.d(tag, msgCallback, tr)
    }

    @JvmStatic
    fun d(tag: String?, tr: Throwable) {
        logWrapper.d(tag, tr)
    }

    @JvmStatic
    fun i(tag: String?, msg: String?) {
        logWrapper.i(tag, msg)
    }

    @JvmStatic
    fun i(tag: String?, msgCallback: () -> String) {
        logWrapper.i(tag, msgCallback)
    }

    @JvmStatic
    fun i(tag: String?, msg: String?, tr: Throwable?) {
        logWrapper.i(tag, msg, tr)
    }

    @JvmStatic
    fun i(tag: String?, msgCallback: () -> String, tr: Throwable?) {
        logWrapper.i(tag, msgCallback, tr)
    }

    @JvmStatic
    fun i(tag: String?, tr: Throwable) {
        logWrapper.i(tag, tr)
    }

    @JvmStatic
    fun w(tag: String?, msg: String) {
        logWrapper.w(tag, msg)
    }

    @JvmStatic
    fun w(tag: String?, msgCallback: () -> String) {
        logWrapper.w(tag, msgCallback)
    }

    @JvmStatic
    fun w(tag: String?, msg: String?, tr: Throwable?) {
        logWrapper.w(tag, msg, tr)
    }

    @JvmStatic
    fun w(tag: String?, msgCallback: () -> String, tr: Throwable?) {
        logWrapper.w(tag, msgCallback, tr)
    }

    @JvmStatic
    fun w(tag: String?, tr: Throwable?) {
        logWrapper.w(tag, tr)
    }

    @JvmStatic
    fun e(tag: String?, msg: String) {
        logWrapper.e(tag, msg)
    }

    @JvmStatic
    fun e(tag: String?, msgCallback: () -> String) {
        logWrapper.e(tag, msgCallback)
    }

    @JvmStatic
    fun e(tag: String?, msg: String?, tr: Throwable?) {
        logWrapper.e(tag, msg, tr)
    }

    @JvmStatic
    fun e(tag: String?, msgCallback: () -> String, tr: Throwable?) {
        logWrapper.e(tag, msgCallback, tr)
    }

    @JvmStatic
    fun e(tag: String?, tr: Throwable) {
        logWrapper.e(tag, tr)
    }

    @JvmStatic
    fun wtf(tag: String?, msg: String?) {
        logWrapper.wtf(tag, msg)
    }

    @JvmStatic
    fun wtf(tag: String?, msgCallback: () -> String) {
        logWrapper.wtf(tag, msgCallback)
    }

    @JvmStatic
    fun wtf(tag: String?, msg: String?, tr: Throwable?) {
        logWrapper.wtf(tag, msg, tr)
    }

    @JvmStatic
    fun wtf(tag: String?, msgCallback: () -> String, tr: Throwable?) {
        logWrapper.wtf(tag, msgCallback, tr)
    }

    @JvmStatic
    fun wtf(tag: String?, tr: Throwable) {
        logWrapper.wtf(tag, tr)
    }

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil#stackTraceString(Throwable) at ImoyaAndroidLogLib.",
        replaceWith = ReplaceWith(
            "if (tr != null) LogUtil.stackTraceString(tr) else \"\"",
            "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun getStackTraceString(tr: Throwable?): String {
        return if (tr != null) LogUtil.stackTraceString(tr) else ""
    }

    /**
     * Log level
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogLevel at ImoyaAndroidLogLib."
    )
    @Suppress("deprecation")
    enum class Level(private val level: Int) {
        NONE(Int.MAX_VALUE), VERBOSE(0), DEBUG(1), INFO(2), WARN(3), ERROR(4), WTF(5);

        companion object {
            @Deprecated(
                level = DeprecationLevel.WARNING,
                message = "Use net.imoya.android.log.LogLevel.from(String) at ImoyaAndroidLogLib.",
                replaceWith = ReplaceWith(
                    "LogLevel.from(s)", "net.imoya.android.log.LogLevel"
                )
            )
            @JvmStatic
            fun from(s: String?): Level {
                return when (s) {
                    "wtf" -> WTF
                    "e", "error" -> ERROR
                    "w", "warn" -> WARN
                    "i", "info" -> INFO
                    "d", "debug" -> DEBUG
                    "v", "verbose" -> VERBOSE
                    else -> NONE
                }
            }

            @Deprecated(
                level = DeprecationLevel.WARNING,
                message = "Use net.imoya.android.log.LogLevel.shouldOutput(LogLevel, LogLevel) at ImoyaAndroidLogLib.",
                replaceWith = ReplaceWith(
                    "LogLevel.shouldOutput(settings, request)", "net.imoya.android.log.LogLevel"
                )
            )
            @JvmStatic
            fun shouldOutput(settings: Level, request: Level): Boolean {
                return settings.level <= request.level
            }
        }
    }
}