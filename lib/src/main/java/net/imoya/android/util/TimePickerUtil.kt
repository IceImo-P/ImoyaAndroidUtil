package net.imoya.android.util

import android.os.Build
import android.widget.TimePicker
import androidx.annotation.RequiresApi

/**
 * [TimePicker] 関連ユーティリティ
 */
@Suppress("unused")
object TimePickerUtil {
    @JvmStatic
    fun getHour(timePicker: TimePicker): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            getHourM(timePicker)
        else
            getHourLegacy(timePicker)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getHourM(timePicker: TimePicker): Int {
        return timePicker.hour
    }

    @Suppress("deprecation")
    @JvmStatic
    fun getHourLegacy(timePicker: TimePicker): Int {
        return timePicker.currentHour
    }

    @JvmStatic
    fun getMinute(timePicker: TimePicker): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            getMinuteM(timePicker)
        else
            getMinuteLegacy(timePicker)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getMinuteM(timePicker: TimePicker): Int {
        return timePicker.hour
    }

    @Suppress("deprecation")
    @JvmStatic
    fun getMinuteLegacy(timePicker: TimePicker): Int {
        return timePicker.currentMinute
    }

    @JvmStatic
    fun setHourAndMinute(timePicker: TimePicker, hour: Int, minute: Int) {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setHourAndMinuteM(timePicker, hour, minute)
        } else {
            setHourAndMinuteLegacy(timePicker, hour, minute)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @JvmStatic
    fun setHourAndMinuteM(timePicker: TimePicker, hour: Int, minute: Int) {
        timePicker.hour = hour
        timePicker.minute = minute
    }

    @Suppress("deprecation")
    @JvmStatic
    fun setHourAndMinuteLegacy(timePicker: TimePicker, hour: Int, minute: Int) {
        timePicker.currentHour = hour
        timePicker.currentMinute = minute
    }
}