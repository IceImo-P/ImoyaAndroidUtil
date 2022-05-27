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

import android.content.Context
import net.imoya.android.log.LogUtil
import net.imoya.android.log.Log as NewLog
import java.util.*

/**
 * Log
 *
 * The logging function at ImoyaAndroidUtil has been moved to ImoyaAndroidLog library.
 *
 * Methods at this class are deprecated. Use ImoyaAndroidLog instead.
 *
 * @author IceImo-P
 */
@Suppress("unused")
object Log {
    /**
     * Initialize log output
     *
     * @param context [Context]
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.init(context)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun init(context: Context) = NewLog.init(context)

    /**
     * Returns date and time string for log
     *
     * @param time RTC datetime
     * @return String for log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil#dateTimeString(Long) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.dateTimeString(time)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun getDateTimeString(time: Long): String = LogUtil.dateTimeString(time)

    /**
     * Returns date and time string for log
     *
     * @param calendar
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil#logString(Calendar) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(calendar)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun getString(calendar: Calendar): String = LogUtil.logString(calendar)

    /**
     * Send a VERBOSE log message if minimum output log level <= [net.imoya.android.log.LogLevel.VERBOSE]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.v(tag, msg)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun v(tag: String?, msg: String) = NewLog.v(tag, msg)

    /**
     * Send a VERBOSE log message if minimum output log level <= [net.imoya.android.log.LogLevel.VERBOSE]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.v(tag, msgCallback)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun v(tag: String?, msgCallback: () -> String) = NewLog.v(tag, msgCallback)

    /**
     * Send a VERBOSE log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.VERBOSE]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.v(tag, msg, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun v(tag: String?, msg: String?, tr: Throwable?) = NewLog.v(tag, msg, tr)

    /**
     * Send a VERBOSE log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.VERBOSE]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.v(tag, msgCallback, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun v(tag: String?, msgCallback: () -> String, tr: Throwable?) = NewLog.v(tag, msgCallback, tr)

    /**
     * Send a VERBOSE log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.VERBOSE]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.v(tag, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun v(tag: String?, tr: Throwable) = NewLog.v(tag, tr)

    /**
     * Send a DEBUG log message if minimum output log level <= [net.imoya.android.log.LogLevel.DEBUG]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.d(tag, msg)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun d(tag: String?, msg: String) = NewLog.d(tag, msg)

    /**
     * Send a DEBUG log message if minimum output log level <= [net.imoya.android.log.LogLevel.DEBUG]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.d(tag, msgCallback)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun d(tag: String?, msgCallback: () -> String) = NewLog.d(tag, msgCallback)

    /**
     * Send a DEBUG log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.DEBUG]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.d(tag, msg, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun d(tag: String?, msg: String?, tr: Throwable?) = NewLog.d(tag, msg, tr)

    /**
     * Send a DEBUG log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.DEBUG]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.d(tag, msgCallback, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun d(tag: String?, msgCallback: () -> String, tr: Throwable?) = NewLog.d(tag, msgCallback, tr)

    /**
     * Send a DEBUG log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.DEBUG]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.d(tag, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun d(tag: String?, tr: Throwable) = NewLog.d(tag, tr)

    /**
     * Send a INFO log message if minimum output log level <= [net.imoya.android.log.LogLevel.INFO]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.i(tag, msg)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun i(tag: String?, msg: String?) = NewLog.i(tag, msg)

    /**
     * Send a INFO log message if minimum output log level <= [net.imoya.android.log.LogLevel.INFO]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.i(tag, msgCallback)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun i(tag: String?, msgCallback: () -> String) = NewLog.i(tag, msgCallback)

    /**
     * Send a INFO log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.INFO]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.i(tag, msg, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun i(tag: String?, msg: String?, tr: Throwable?) = NewLog.i(tag, msg, tr)

    /**
     * Send a INFO log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.INFO]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.i(tag, msgCallback, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun i(tag: String?, msgCallback: () -> String, tr: Throwable?) = NewLog.i(tag, msgCallback, tr)

    /**
     * Send a INFO log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.INFO]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.i(tag, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun i(tag: String?, tr: Throwable) = NewLog.i(tag, tr)

    /**
     * Send a WARN log message if minimum output log level <= [net.imoya.android.log.LogLevel.WARN]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.w(tag, msg)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun w(tag: String?, msg: String) = NewLog.w(tag, msg)

    /**
     * Send a WARN log message if minimum output log level <= [net.imoya.android.log.LogLevel.WARN]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.w(tag, msgCallback)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun w(tag: String?, msgCallback: () -> String) = NewLog.w(tag, msgCallback)

    /**
     * Send a WARN log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.WARN]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.w(tag, msg, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun w(tag: String?, msg: String?, tr: Throwable?) = NewLog.w(tag, msg, tr)

    /**
     * Send a WARN log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.WARN]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.w(tag, msgCallback, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun w(tag: String?, msgCallback: () -> String, tr: Throwable?) = NewLog.w(tag, msgCallback, tr)

    /**
     * Send a WARN log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.WARN]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.w(tag, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun w(tag: String?, tr: Throwable?) = NewLog.w(tag, tr)

    /**
     * Send a ERROR log message if minimum output log level <= [net.imoya.android.log.LogLevel.ERROR]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.e(tag, msg)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun e(tag: String?, msg: String) = NewLog.e(tag, msg)

    /**
     * Send a ERROR log message if minimum output log level <= [net.imoya.android.log.LogLevel.ERROR]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.e(tag, msgCallback)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun e(tag: String?, msgCallback: () -> String) = NewLog.e(tag, msgCallback)

    /**
     * Send a ERROR log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.ERROR]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.e(tag, msg, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun e(tag: String?, msg: String?, tr: Throwable?) = NewLog.e(tag, msg, tr)

    /**
     * Send a ERROR log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.ERROR]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.e(tag, msgCallback, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun e(tag: String?, msgCallback: () -> String, tr: Throwable?) = NewLog.e(tag, msgCallback, tr)

    /**
     * Send a ERROR log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.ERROR]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.e(tag, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun e(tag: String?, tr: Throwable) = NewLog.e(tag, tr)

    /**
     * Send a WTF log message if minimum output log level <= [net.imoya.android.log.LogLevel.WTF]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.wtf(tag, msg)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun wtf(tag: String?, msg: String?) = NewLog.wtf(tag, msg)

    /**
     * Send a WTF log message if minimum output log level <= [net.imoya.android.log.LogLevel.WTF]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.wtf(tag, msgCallback)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun wtf(tag: String?, msgCallback: () -> String) = NewLog.wtf(tag, msgCallback)

    /**
     * Send a WTF log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.WTF]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.wtf(tag, msg, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun wtf(tag: String?, msg: String?, tr: Throwable?) = NewLog.wtf(tag, msg, tr)

    /**
     * Send a WTF log message and log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.WTF]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msgCallback The function which returns message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.wtf(tag, msgCallback, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun wtf(tag: String?, msgCallback: () -> String, tr: Throwable?) = NewLog.wtf(tag, msgCallback, tr)

    /**
     * Send a WTF log the exception if minimum output log level <= [net.imoya.android.log.LogLevel.WTF]
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith("Log.wtf(tag, tr)", "net.imoya.android.log.Log")
    )
    @JvmStatic
    fun wtf(tag: String?, tr: Throwable) = NewLog.wtf(tag, tr)

    /**
     * @see android.util.Log.getStackTraceString
     */
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.Log at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "Log.getStackTraceString(tr)", "net.imoya.android.log.Log"
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
        message = "Use net.imoya.android.log.LogLevel at ImoyaAndroidLog."
    )
    @Suppress("deprecation")
    enum class Level(private val level: Int) {
        NONE(Int.MAX_VALUE), VERBOSE(0), DEBUG(1), INFO(2), WARN(3), ERROR(4), WTF(5);

        companion object {
            @Deprecated(
                level = DeprecationLevel.WARNING,
                message = "Use net.imoya.android.log.LogLevel.from(String) at ImoyaAndroidLog.",
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
                message = "Use net.imoya.android.log.LogLevel.shouldOutput(LogLevel, LogLevel) at ImoyaAndroidLog.",
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