package net.imoya.android.util

import android.view.View

/**
 * Utilities for Android [View]s
 */
@Suppress("unused")
object ViewUtil {
    /**
     * Set [View.setVisibility] to [View.VISIBLE] or [View.GONE]
     *
     * @param view [View]
     * @param visible true if set [View.VISIBLE], otherwise [View.GONE]
     */
    @JvmStatic
    fun setVisibleOrGone(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}