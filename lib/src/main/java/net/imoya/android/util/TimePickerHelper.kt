package net.imoya.android.util

import android.os.Build
import android.widget.TimePicker
import androidx.annotation.RequiresApi

/**
 * [TimePicker] helper
 *
 * Accesses value that Android API level dependent
 */
@Suppress("unused")
class TimePickerHelper(private val timePicker: TimePicker) {
    /**
     * Call [TimePicker.getHour] or its legacy API
     *
     * @return the currently selected hour, in the range (0-23)
     * @see [TimePicker.getHour]
     */
    fun getHour(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            getHourM()
        else
            getHourLegacy()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getHourM(): Int {
        return timePicker.hour
    }

    @Suppress("deprecation")
    fun getHourLegacy(): Int {
        return timePicker.currentHour
    }

    /**
     * Call [TimePicker.getMinute] or its legacy API
     *
     * @return the currently selected minute, in the range (0-59)
     * @see [TimePicker.getMinute]
     */
    fun getMinute(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            getMinuteM()
        else
            getMinuteLegacy()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getMinuteM(): Int {
        return timePicker.minute
    }

    @Suppress("deprecation")
    fun getMinuteLegacy(): Int {
        return timePicker.currentMinute
    }

    /**
     * Call [TimePicker.setHour], [TimePicker.setMinute] or its legacy API
     *
     * @param hour the hour to set, in the range (0-23)
     * @param minute the minute to set, in the range (0-59)
     */
    fun setHourAndMinute(hour: Int, minute: Int) {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setHourAndMinuteM(hour, minute)
        } else {
            setHourAndMinuteLegacy(hour, minute)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setHourAndMinuteM(hour: Int, minute: Int) {
        timePicker.hour = hour
        timePicker.minute = minute
    }

    @Suppress("deprecation")
    fun setHourAndMinuteLegacy(hour: Int, minute: Int) {
        timePicker.currentHour = hour
        timePicker.currentMinute = minute
    }
}