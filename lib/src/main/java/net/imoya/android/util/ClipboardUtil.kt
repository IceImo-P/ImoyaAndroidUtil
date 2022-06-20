package net.imoya.android.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * Clipboard utilities
 *
 * @author IceImo-P
 */
@Suppress("unused")
object ClipboardUtil {
    /**
     * Copy text to clipboard
     *
     * @param context [Context]
     * @param label Label for text
     * @param text Text to copy
     */
    @Suppress("weaker")
    fun copyText(context: Context, label: CharSequence, text: CharSequence) {
        val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        manager.setPrimaryClip(ClipData.newPlainText(label, text))
    }

    /**
     * Copy text to clipboard
     *
     * @param context [Context]
     * @param text Text to copy
     */
    fun copyText(context: Context, text: CharSequence) = copyText(context, text, text)
}