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