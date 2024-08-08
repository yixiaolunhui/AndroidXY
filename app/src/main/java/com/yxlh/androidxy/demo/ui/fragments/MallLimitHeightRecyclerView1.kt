package com.yxlh.androidxy.demo.ui.fragments

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxlh.androidxy.R

/**
 * 自定义RecyclerView，当高度低于限定高度时由外部NestedScrollView滑动；
 * 当高度超过限定高度时，支持自身滑动，并在滑动到顶部或底部时传递滑动事件给NestedScrollView。
 */
class MallLimitHeightRecyclerView1 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0,
) : RecyclerView(context, attrs, defStyle) {

    private var limitHeight: Int = 0
    private var lastY: Float = 0f
    private val gestureDetector: GestureDetector

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.LimitHeightRecyclerView, 0, 0
        ).apply {
            try {
                limitHeight = getDimensionPixelSize(R.styleable.LimitHeightRecyclerView_limitHeight, 0)
            } finally {
                recycle()
            }
        }

        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float,
            ): Boolean {
                return true
            }
        })
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

//    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
//        if (gestureDetector.onTouchEvent(e)) {
//            if (measuredHeight >= limitHeight) {
//                requestDisallowInterceptTouchEvent(false)
//                return super.onInterceptTouchEvent(e)
//            } else {
//                requestDisallowInterceptTouchEvent(true)
//                return false
//            }
//        }
//        return super.onInterceptTouchEvent(e)
//
//    }

//    override fun onTouchEvent(e: MotionEvent?): Boolean {
//        if (e == null) return super.onTouchEvent(e)
//        when (e.action) {
//            MotionEvent.ACTION_DOWN -> lastY = e.y
//            MotionEvent.ACTION_MOVE -> {
//                val deltaY = e.y - lastY
//                val layoutManager = layoutManager as? LinearLayoutManager
//                if (layoutManager != null) {
//                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
//                    val totalItemCount = layoutManager.itemCount
//
//                    if (deltaY > 0 && firstVisibleItemPosition == 0 && findViewHolderForAdapterPosition(0)?.itemView?.top == 0) {
//                        // 向下滑动并滑动到顶部
//                        requestDisallowInterceptTouchEvent(false)
//                    } else if (deltaY < 0 && lastVisibleItemPosition == totalItemCount - 1 && findViewHolderForAdapterPosition(totalItemCount - 1)?.itemView?.bottom == height) {
//                        // 向上滑动并滑动到底部
//                        requestDisallowInterceptTouchEvent(false)
//                    } else {
//                        requestDisallowInterceptTouchEvent(true)
//                    }
//                }
//            }
//
//            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                requestDisallowInterceptTouchEvent(false)
//            }
//        }
//
//        return super.onTouchEvent(e)
//    }
}
