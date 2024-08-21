package com.yxlh.androidxy.demo.ui.fragments

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.yxlh.androidxy.R

/**
 * 自定义RecyclerView，当高度低于限定高度时由外部NestedScrollView滑动；
 * 当高度超过限定高度时，支持自身滑动，并在滑动到顶部或底部时传递滑动事件给NestedScrollView。
 */
class MallLimitHeightRecyclerView3 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0,
) : RecyclerView(context, attrs, defStyle) {

    private var limitHeight: Int = 0
    private var lastY: Float = 0f

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.LimitHeightRecyclerView, 0, 0
        ).apply {
            try {
                limitHeight =
                    getDimensionPixelSize(R.styleable.LimitHeightRecyclerView_limitHeight, 0)
            } finally {
                recycle()
            }
        }
    }

    fun setLimitHeight(limitHeight: Int) {
        if (this.limitHeight != limitHeight) {
            this.limitHeight = limitHeight
            requestLayout()
        }
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (limitHeight in 1 until measuredHeight) {
            setMeasuredDimension(measuredWidth, limitHeight)
        }
    }


}
