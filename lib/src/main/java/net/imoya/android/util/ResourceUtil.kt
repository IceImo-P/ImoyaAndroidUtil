package net.imoya.android.util

import android.content.Context
import android.graphics.drawable.Drawable

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
     * @return Color
     */
    @JvmStatic
    fun loadColor(context: Context, resId: Int): Int {
        return ResourcesCompat.getColor(context.resources, resId, null)
    }

    /**
     * Get drawable from resource
     *
     * @param context [Context]
     * @param resId Resource ID
     * @return [Drawable] if drawable resource exists, otherwise null.
     */
    @JvmStatic
    fun loadDrawable(context: Context, resId: Int): Drawable? {
        return ResourcesCompat.getDrawable(context.resources, resId, null)
    }
}