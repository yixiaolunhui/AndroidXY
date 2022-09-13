package com.yxlh.androidxy.demo.ui.avatar

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *@author zwl
 *@date on 2022/9/2
 */
object AvatarOverlayHelper {

    fun init(context: Context, recyclerView: RecyclerView, reverse: Boolean = false) {
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
            reverseLayout = reverse
        }

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                if (reverse) {
                    val size = (recyclerView.adapter?.itemCount ?: 0) - 1
                    val index = parent.getChildLayoutPosition(view)
                    if (size != index) {
                        outRect.left = dip2px(context, -15f)
                    }
                } else {
                    if (parent.getChildLayoutPosition(view) != 0) {
                        outRect.left = dip2px(context, -15f)
                    }
                }

            }
        })
    }


    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}