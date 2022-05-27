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
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi

@Suppress("unused")
object DragAndDropUtil {
    @JvmStatic
    fun startDragAndDrop(view: View, data: ClipData, shadowBuilder: View.DragShadowBuilder, myLocalState: Any?, flags: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            startDragAndDropN(view, data, shadowBuilder, myLocalState, flags)
        } else {
            startDragAndDropLegacy(view, data, shadowBuilder, myLocalState, flags)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @JvmStatic
    fun startDragAndDropN(view: View, data: ClipData, shadowBuilder: View.DragShadowBuilder, myLocalState: Any?, flags: Int) {
        view.startDragAndDrop(data, shadowBuilder, myLocalState, flags)
    }

    @Suppress("deprecation")
    @JvmStatic
    fun startDragAndDropLegacy(view: View, data: ClipData, shadowBuilder: View.DragShadowBuilder, myLocalState: Any?, flags: Int) {
        view.startDrag(data, shadowBuilder, myLocalState, flags)
    }
}