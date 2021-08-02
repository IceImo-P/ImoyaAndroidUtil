package net.imoya.android.util

import android.content.Context
import android.util.Log
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/**
 * Log
 *
 * @author IceImo-P
 */
@Suppress("unused")
object Log {
    /**
     * Output log level
     */
    private var logLevel = Level.INFO

    @JvmStatic
    internal fun shouldOutput(request: Level): Boolean {
        return Level.shouldOutput(logLevel, request)
    }

    /**
     * Initialize log output
     *
     * @param context [Context]
     */
    @JvmStatic
    fun init(context: Context) {
        logLevel = Level.from(context.getString(R.string.imoya_log_level))
    }

    /**
     * Returns date and time string for log
     *
     * @param time RTC datetime
     * @return String for log
     */
    @JvmStatic
    fun getDateTimeString(time: Long): String {
        val calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.US)
        calendar.timeInMillis = time
        return getString(calendar)
    }

    /**
     * Returns date and time string for log
     *
     * @param calendar
     */
    @JvmStatic
    fun getString(calendar: Calendar): String {
        return calendar[Calendar.YEAR].toString() + "/" + String.format(
            Locale.US,
            "%02d",
            calendar[Calendar.MONTH] + 1
        ) + "/" + String.format(
            Locale.US,
            "%02d",
            calendar[Calendar.DAY_OF_MONTH]
        ) + " " + String.format(
            Locale.US,
            "%02d",
            calendar[Calendar.HOUR_OF_DAY]
        ) + ":" + String.format(
            Locale.US,
            "%02d",
            calendar[Calendar.MINUTE]
        ) + ":" + String.format(
            Locale.US,
            "%02d",
            calendar[Calendar.SECOND]
        )
    }

    @JvmStatic
    fun v(tag: String?, msg: String) {
        if (shouldOutput(Level.VERBOSE)) Log.v(tag, msg)
    }

    @JvmStatic
    fun v(tag: String?, msg: String?, tr: Throwable?) {
        if (shouldOutput(Level.VERBOSE)) Log.v(tag, msg, tr)
    }

    @JvmStatic
    fun v(tag: String?, tr: Throwable) {
        if (shouldOutput(Level.VERBOSE)) Log.v(tag, tr.toString(), tr)
    }

    @JvmStatic
    fun d(tag: String?, msg: String) {
        if (shouldOutput(Level.DEBUG)) Log.d(tag, msg)
    }

    @JvmStatic
    fun d(tag: String?, msg: String?, tr: Throwable?) {
        if (shouldOutput(Level.DEBUG)) Log.d(tag, msg, tr)
    }

    @JvmStatic
    fun d(tag: String?, tr: Throwable) {
        if (shouldOutput(Level.DEBUG)) Log.d(tag, tr.toString(), tr)
    }

    @JvmStatic
    fun i(tag: String?, msg: String?) {
        if (shouldOutput(Level.INFO)) Log.i(tag, msg!!)
    }

    @JvmStatic
    fun i(tag: String?, msg: String?, tr: Throwable?) {
        if (shouldOutput(Level.INFO)) Log.i(tag, msg, tr)
    }

    @JvmStatic
    fun i(tag: String?, tr: Throwable) {
        if (shouldOutput(Level.INFO)) Log.i(tag, tr.toString(), tr)
    }

    @JvmStatic
    fun w(tag: String?, msg: String) {
        if (shouldOutput(Level.WARN)) Log.w(tag, msg)
    }

    @JvmStatic
    fun w(tag: String?, msg: String?, tr: Throwable?) {
        if (shouldOutput(Level.WARN)) Log.w(tag, msg, tr)
    }

    @JvmStatic
    fun w(tag: String?, tr: Throwable?) {
        if (shouldOutput(Level.WARN)) Log.w(tag, tr)
    }

    @JvmStatic
    fun e(tag: String?, msg: String) {
        if (shouldOutput(Level.ERROR)) Log.e(tag, msg)
    }

    @JvmStatic
    fun e(tag: String?, msg: String?, tr: Throwable?) {
        if (shouldOutput(Level.ERROR)) Log.e(tag, msg, tr)
    }

    @JvmStatic
    fun e(tag: String?, tr: Throwable) {
        if (shouldOutput(Level.ERROR)) Log.e(tag, tr.toString(), tr)
    }

    @JvmStatic
    fun wtf(tag: String?, msg: String?) {
        if (shouldOutput(Level.WTF)) Log.wtf(tag, msg)
    }

    @JvmStatic
    fun wtf(tag: String?, msg: String?, tr: Throwable?) {
        if (shouldOutput(Level.WTF)) Log.wtf(tag, msg, tr)
    }

    @JvmStatic
    fun wtf(tag: String?, tr: Throwable) {
        if (shouldOutput(Level.WTF)) Log.wtf(tag, tr)
    }

    @JvmStatic
    fun getStackTraceString(tr: Throwable?): String {
        return Log.getStackTraceString(tr)
    }

    /**
     * Log level
     */
    enum class Level(private val level: Int) {
        NONE(Int.MAX_VALUE), VERBOSE(0), DEBUG(1), INFO(2), WARN(3), ERROR(4), WTF(5);

        companion object {
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

            @JvmStatic
            fun shouldOutput(settings: Level, request: Level): Boolean {
                return settings.level <= request.level
            }
        }
    }
}