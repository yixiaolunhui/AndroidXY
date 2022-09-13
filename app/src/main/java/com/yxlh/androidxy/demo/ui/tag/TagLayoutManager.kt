package com.yxlh.androidxy.demo.ui.tag

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.google.android.flexbox.FlexboxLayoutManager

/**
 *@describe 流式标签LayoutManager
 *@author zwl
 *@date on 2022/8/27
 */
class TagLayoutManager constructor(context: Context, var space: Int) : FlexboxLayoutManager(context) {

    /**
     * 是否采用自动测量
     */
    override fun isAutoMeasureEnabled() = true

    override fun canScrollVertically() = true

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        ).also {
            it.setMargins(space, space, space, space)
        }
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        //当前屏幕上所有的HolderView与屏幕分离,用来重新布局
        detachAndScrapAttachedViews(recycler)
        //当前行宽度
        var currentLineWidth = 0
        //当前行Top高度
        var currentLineTop = 0
        //当前行最大的子布局高度
        var currentLineMaxHeight = 0

        //高度
        var heightTemp = 0

        for (index in 0 until itemCount) {
            //获取子View
            val childView: View = recycler.getViewForPosition(index)
            //获取子View layoutParams
            val params = childView.layoutParams as RecyclerView.LayoutParams
            addView(childView)
            //测量
            measureChildWithMargins(childView, 0, 0)
            //获取子View的宽度 包含Margin
            val width = getDecoratedMeasuredWidth(childView) + params.leftMargin + params.rightMargin
            //获取子View的高度 包含Margin
            val height = getDecoratedMeasuredHeight(childView) + params.topMargin + params.bottomMargin
            //更新当前已布的子View的宽度
            currentLineWidth += width

            //当前已布局行宽小于 RecyclerView 宽度
            if (currentLineWidth <= getWidth()) {
                val left = currentLineWidth - width + params.leftMargin
                val top = currentLineTop + params.topMargin
                val right = currentLineWidth - params.rightMargin
                val bottom = currentLineTop + height - params.bottomMargin
                //布局View
                layoutDecorated(childView, left, top, right, bottom)
                //更新当前行最高的子View的高度
                currentLineMaxHeight = currentLineMaxHeight.coerceAtLeast(height)
            }
            // 重开新行（大于RecyclerView宽度换行）
            else {
                //更新新行已用宽度
                currentLineWidth = width
                //更新当前行子View最大高度
                currentLineMaxHeight = height
                //更新新行Top高度
                currentLineTop += height
                val left = currentLineWidth - width + params.leftMargin
                val top = currentLineTop + params.topMargin
                val right = currentLineWidth - params.rightMargin
                val bottom = currentLineTop + height - params.bottomMargin
                //布局View
                layoutDecorated(childView, left, top, right, bottom)
            }

            heightTemp += height
        }
    }


}