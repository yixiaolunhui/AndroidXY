package com.yxlh.androidxy.demo.ui.viewpager.vp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

interface AutoHeightPager {
    fun getView(position: Int): View?
}

class AutoHeightViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    private var lastWidthMeasureSpec: Int = 0
    private var currentHeight: Int = 0
    private var lastPosition: Int = 0
    private var isScrolling: Boolean = false

    init {
        addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (positionOffset == 0f) {
                    isScrolling = false
                    requestLayout()
                    return
                }

                val srcPosition = if (position >= lastPosition) position else position + 1
                val destPosition = if (position >= lastPosition) position + 1 else position

                val srcHeight = getViewHeight(srcPosition)
                val destHeight = getViewHeight(destPosition)

                currentHeight = (srcHeight + (destHeight - srcHeight) *
                        if (position >= lastPosition) positionOffset else 1 - positionOffset).toInt()

                isScrolling = true
                requestLayout()
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == SCROLL_STATE_IDLE) {
                    lastPosition = currentItem
                }
            }
        })
    }

    override fun setAdapter(adapter: PagerAdapter?) {
        require(adapter == null || adapter is AutoHeightPager) { "PagerAdapter must implement AutoHeightPager." }
        super.setAdapter(adapter)
    }

    private fun getViewHeight(position: Int): Int {
        val adapter = adapter as? AutoHeightPager ?: return 0

        return run {
            val view = adapter.getView(position) ?: return 0
            view.measure(
                lastWidthMeasureSpec,
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            val height = view.measuredHeight
            height
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        lastWidthMeasureSpec = widthMeasureSpec
        var heightSpec = heightMeasureSpec
        if (isScrolling) {
            heightSpec = MeasureSpec.makeMeasureSpec(currentHeight, MeasureSpec.EXACTLY)
        } else {
            getViewHeight(currentItem).takeIf { it > 0 }?.let {
                heightSpec = MeasureSpec.makeMeasureSpec(it, MeasureSpec.EXACTLY)
            }
        }
        super.onMeasure(widthMeasureSpec, heightSpec)
    }
}