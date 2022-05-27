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

import android.os.Build
import android.widget.TimePicker
import androidx.annotation.RequiresApi

/**
 * [TimePicker] helper
 *
 * Accesses value that Android API level dependent
 *
 * @author IceImo-P
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