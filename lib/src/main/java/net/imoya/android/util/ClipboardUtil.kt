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