package net.imoya.android.util

import android.view.View
import android.view.ViewGroup

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
    fun setVisibleOrGone(view: View?, visible: Boolean) {
        view?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * Set [View.setEnabled] recursively
     *
     * @param view [View]
     * @param enabled value for set [View.setEnabled]
     */
    @JvmStatic
    fun setDeepEnabled(view: View?, enabled: Boolean) {
        view?.isEnabled = enabled
        if (view is ViewGroup) {
            val count = view.childCount
            for (i in 0 until count) {
                val child = view.getChildAt(i)
                setDeepEnabled(child, enabled)
            }
        }
    }
}