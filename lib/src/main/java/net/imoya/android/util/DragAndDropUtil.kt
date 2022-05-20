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