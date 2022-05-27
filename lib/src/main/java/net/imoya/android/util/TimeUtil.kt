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
import java.util.*

/**
 * 時刻関連ユーティリティ
 */
@Suppress("unused")
object TimeUtil {
    /**
     * Returns string for display time to user formatted by resource.
     *
     * @param context   [Context]
     * @param hourOfDay hour of day(0-23)
     * @param minute    minutes(0-59)
     * @param hour24    24時間制で表示する場合はtrue, 12時間制で表示する場合はfalse。
     * @return String for display time to user
     */
    @JvmStatic
    fun formatTime(context: Context, hourOfDay: Int, minute: Int, hour24: Boolean): String {
        val result: String
        if (hour24) {
            result = context.getString(R.string.time_format_24)
                .replace("#HOUR#".toRegex(), hourOfDay.toString())
                .replace("#MINUTE#".toRegex(), String.format(Locale.US, "%02d", minute))
        } else {
            val amPm: String
            val hour: Int
            if (hourOfDay < 12) {
                amPm = context.getString(R.string.time_am)
                hour = hourOfDay
            } else {
                amPm = context.getString(R.string.time_pm)
                hour = hourOfDay - 12
            }
            result = context.getString(R.string.time_format)
                .replace("#HOUR#".toRegex(), hour.toString())
                .replace("#MINUTE#".toRegex(), String.format(Locale.US, "%02d", minute))
                .replace("#AMPM#".toRegex(), amPm)
        }
        return result
    }
}