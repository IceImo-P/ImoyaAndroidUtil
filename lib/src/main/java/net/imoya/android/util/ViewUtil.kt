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
            setEnabledDescendants(view, enabled)
        }
    }

    /**
     * Set [View.setEnabled] to all descendant
     *
     * @param viewGroup [ViewGroup]
     * @param enabled value for set [View.setEnabled]
     */
    @JvmStatic
    fun setEnabledDescendants(viewGroup: ViewGroup, enabled: Boolean) {
        val list: ArrayList<View> = ArrayList()
        for (i in 0 until viewGroup.childCount) {
            setEnabledDescendantsInternal(viewGroup.getChildAt(i), enabled, list)
        }
    }

    private fun setEnabledDescendantsInternal(
        view: View?,
        enabled: Boolean,
        list: ArrayList<View>
    ) {
        if (view != null && !list.any { v -> v === view }) {
            view.isEnabled = enabled
            list.add(view)
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    setEnabledDescendantsInternal(view.getChildAt(i), enabled, list)
                }
            }
        }
    }
}