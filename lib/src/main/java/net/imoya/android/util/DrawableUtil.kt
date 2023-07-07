package net.imoya.android.util

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat

/**
 * Utilities for [Drawable]
 */
@Suppress("unused")
object DrawableUtil {
    /**
     * Set tint color to [Drawable]
     *
     * @param drawable [Drawable]
     * @param color Tint color to set
     * @return [Drawable]
     */
    @JvmStatic
    fun setTintColor(drawable: Drawable, @ColorInt color: Int): Drawable {
        UtilLog.v(TAG, "setTintColor: start")
        val wrapped = DrawableCompat.wrap(drawable).mutate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UtilLog.v(TAG, "setTintColor: >= LOLLIPOP")
            DrawableCompat.setTint(wrapped, color)
            DrawableCompat.setTintMode(wrapped, PorterDuff.Mode.SRC_IN)
        } else {
            UtilLog.v(TAG, "setTintColor: < LOLLIPOP")
            setColorFilterLegacy(wrapped, color)
        }
        return wrapped
    }

    @JvmStatic
    @Suppress("deprecation")
    private fun setColorFilterLegacy(drawable: Drawable, @ColorInt color: Int) {
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    /**
     * Set tint color to [Drawable]
     *
     * @param context [Context]
     * @param drawableRes Resource ID of [Drawable]
     * @param colorRes Resource ID of tint color
     * @return [Drawable]
     */
    @JvmStatic
    fun setTintColor(
        context: Context,
        @DrawableRes drawableRes: Int,
        @ColorRes colorRes: Int
    ): Drawable {
        return setTintColor(
            ResourceUtil.loadDrawable(context, drawableRes)
                ?: throw IllegalArgumentException("drawable not found"),
            ResourceUtil.loadColor(context, colorRes)
        )
    }

    /**
     * Tag for log
     */
    private const val TAG = "DrawableUtil"
}