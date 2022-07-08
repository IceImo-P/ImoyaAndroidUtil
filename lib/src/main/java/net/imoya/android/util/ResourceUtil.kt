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
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

import androidx.core.content.res.ResourcesCompat

/**
 * Utilities for resources
 */
@Suppress("unused")
object ResourceUtil {
    /**
     * Get color from resource
     *
     * @param context [Context]
     * @param resId Resource ID
     * @return Color if resource exists, otherwise 0
     */
    @JvmStatic
    @ColorInt
    fun loadColor(context: Context, @ColorRes resId: Int): Int {
        return ResourcesCompat.getColor(context.resources, resId, null)
    }

    /**
     * Get color from current theme
     *
     * @param context [Context]
     * @param resId Resource ID
     * @return Color if resource exists, otherwise 0
     */
    @JvmStatic
    @ColorInt
    fun loadColorFromTheme(context: Context, @AttrRes resId: Int): Int {
        UtilLog.v(TAG, "loadColorFromTheme: start")
        val typedValue = TypedValue()
        return if (context.theme.resolveAttribute(resId, typedValue, true)) {
            UtilLog.v(TAG, "loadColorFromTheme: exists")
            typedValue.data
        }
        else{
            UtilLog.v(TAG, "loadColorFromTheme: not found")
            0
        }
    }

    /**
     * Get drawable from resource
     *
     * @param context [Context]
     * @param resId Resource ID
     * @return [Drawable] if drawable resource exists, otherwise null.
     */
    @JvmStatic
    fun loadDrawable(context: Context, @DrawableRes resId: Int): Drawable? {
        UtilLog.v(TAG, "loadDrawable: start")
        return ResourcesCompat.getDrawable(context.resources, resId, null)
    }

    /**
     * Tag for log
     */
    private const val TAG = "ResourceUtil"
}