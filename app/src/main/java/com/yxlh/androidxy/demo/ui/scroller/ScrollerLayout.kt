package com.yxlh.androidxy.demo.ui.scroller

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.Scroller
import androidx.recyclerview.widget.RecyclerView

class ScrollerLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val mScroller = Scroller(context)
    private var lastY = 0
    private var downY = 0
    private var contentHeight = 0
    private var isRecyclerViewAtTop = false
    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop

    init {
        orientation = VERTICAL
        post {
            setupRecyclerViews()
        }
    }

    private fun setupRecyclerViews() {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child is RecyclerView) {
                child.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        isRecyclerViewAtTop = !recyclerView.canScrollVertically(-1)
                    }
                })
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val bottomBar = getChildAt(0)
        contentHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child is RecyclerView) {
                contentHeight += child.measuredHeight
            }
        }
        bottomBar.layout(0, measuredHeight - bottomBar.measuredHeight, measuredWidth, measuredHeight)
        for (i in 1 until childCount) {
            val child = getChildAt(i)
            if (child is RecyclerView) {
                child.layout(0, measuredHeight, measuredWidth, measuredHeight + contentHeight)
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downY = ev.y.toInt()
                lastY = downY
            }

            MotionEvent.ACTION_MOVE -> {
                val currentY = ev.y.toInt()
                val dy = currentY - downY

                if (isRecyclerViewAtTop && dy > touchSlop) {
                    lastY = currentY
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isTouchInsideChild(event)) return false
                if (!mScroller.isFinished) {
                    mScroller.abortAnimation()
                }
                lastY = event.y.toInt()
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (!isTouchInsideChild(event)) return false
                val currentY = event.y.toInt()
                val dy = lastY - currentY
                val scrollY = scrollY + dy

                if (scrollY < 0) {
                    scrollTo(0, 0)
                } else if (scrollY > contentHeight) {
                    scrollTo(0, contentHeight)
                } else {
                    scrollBy(0, dy)
                }

                lastY = currentY
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val threshold = contentHeight / 2
                if (scrollY > threshold) {
                    showNavigation()
                } else {
                    closeNavigation()
                }
                return true
            }
        }
        return false
    }

    private fun isTouchInsideChild(event: MotionEvent): Boolean {
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (isViewUnder(child, x, y)) {
                return true
            }
        }
        return false
    }

    private fun isViewUnder(view: View?, x: Int, y: Int): Boolean {
        if (view == null) return false
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val viewX = location[0]
        val viewY = location[1]
        return x >= viewX && x < viewX + view.width && y >= viewY && y < viewY + view.height
    }

    fun showNavigation() {
        val dy = contentHeight - scrollY
        mScroller.startScroll(scrollX, scrollY, 0, dy, 500)
        invalidate()
    }

    private fun closeNavigation() {
        val dy = -scrollY
        mScroller.startScroll(scrollX, scrollY, 0, dy, 500)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            postInvalidateOnAnimation()
        }
    }
}
