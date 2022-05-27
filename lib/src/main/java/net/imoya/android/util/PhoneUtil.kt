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
import android.media.AudioManager
import java.lang.Exception

@Suppress("unused")
object PhoneUtil {
    /**
     * 携帯電話がマナーモード(バイブレーターのみ使用する状態)か着信音量ゼロの場合に true を返します。
     *
     * @param context [Context]
     * @return true if device is in silent mode or ringer sound volume is 0, otherwise false.
     */
    @JvmStatic
    fun isSilentMode(context: Context): Boolean {
        return try {
            val audio = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audio.ringerMode == AudioManager.RINGER_MODE_SILENT || audio.getStreamVolume(
                AudioManager.STREAM_RING
            ) == 0
        } catch (ex: Exception) {
            // エラーが発生したら、とりあえずサイレントモードであるとする。
            true
        }
    }
}