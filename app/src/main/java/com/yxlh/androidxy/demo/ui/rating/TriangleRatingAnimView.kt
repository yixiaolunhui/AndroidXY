package com.yxlh.androidxy.demo.ui.rating

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.ColorUtils
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.yxlh.androidxy.R


fun Context.dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

/**
 * 三角形评分控件
 */
class TriangleRatingAnimView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    var maxRating: Int = 5
        set(value) {
            field = value
            invalidate()
        }
    var upRating: Int = 0
        set(value) {
            field = value
            if (enableAnimation) {
                animateRating()
            } else {
                animatedUpRating = upRating
                invalidate()
            }
        }
    var leftRating: Int = 0
        set(value) {
            field = value
            if (enableAnimation) {
                animateRating()
            } else {
                animatedLeftRating = leftRating
                invalidate()
            }
        }
    var rightRating: Int = 0
        set(value) {
            field = value
            if (enableAnimation) {
                animateRating()
            } else {
                animatedRightRating = rightRating
                invalidate()
            }
        }
    private var strokeColor: Int = Color.GRAY
    private var strokeWidth: Float = context.dpToPx(1.5f)
    private var ratingStrokeColor: Int = Color.RED
    private var ratingStrokeWidth: Float = context.dpToPx(2.5f)
    private var animatedUpRating = 0
    private var animatedLeftRating = 0
    private var animatedRightRating = 0
    private var enableAnimation: Boolean = true

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = strokeColor
        strokeWidth = this@TriangleRatingAnimView.strokeWidth
    }

    private val outerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = ratingStrokeColor
        strokeWidth = this@TriangleRatingAnimView.ratingStrokeWidth
    }

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ColorUtils.setAlphaComponent(ratingStrokeColor, (0.3 * 255).toInt())
    }
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = ratingStrokeColor
        strokeWidth = context.dpToPx(1.5f)
    }
    private val circleFillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.TriangleRatingAnimView) {
            maxRating = getInt(R.styleable.TriangleRatingAnimView_maxRating, 5)
            upRating = getInt(R.styleable.TriangleRatingAnimView_upRating, 0)
            leftRating = getInt(R.styleable.TriangleRatingAnimView_leftRating, 0)
            rightRating = getInt(R.styleable.TriangleRatingAnimView_rightRating, 0)
            strokeColor = getColor(R.styleable.TriangleRatingAnimView_strokeColor, Color.GRAY)
            strokeWidth = context.dpToPx(getDimension(R.styleable.TriangleRatingAnimView_strokeWidth, 2f))
            ratingStrokeColor = getColor(R.styleable.TriangleRatingAnimView_ratingStrokeColor, Color.RED)
            ratingStrokeWidth = context.dpToPx(getDimension(R.styleable.TriangleRatingAnimView_ratingStrokeWidth, 4f))
            enableAnimation = getBoolean(R.styleable.TriangleRatingAnimView_enableAnimation, true)
        }
    }

    private fun animateRating() {
        val animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 300
            interpolator = LinearOutSlowInInterpolator()
            addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Float
                animatedUpRating = (upRating * animatedValue).toInt()
                animatedLeftRating = (leftRating * animatedValue).toInt()
                animatedRightRating = (rightRating * animatedValue).toInt()
                invalidate()
            }
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = measuredWidth.toFloat()
        val height = measuredHeight.toFloat()
        val circleRadius = context.dpToPx(5f)
        val padding = circleRadius + context.dpToPx(2f)

        val p1 = width / 2 to padding
        val p2 = padding to height - padding
        val p3 = width - padding to height - padding

        // 绘制外部三角形
        val path = Path().apply {
            moveTo(p1.first, p1.second)
            lineTo(p2.first, p2.second)
            lineTo(p3.first, p3.second)
            close()
        }
        canvas.drawPath(path, paint)

        val centroidX = (p1.first + p2.first + p3.first) / 3
        val centroidY = (p1.second + p2.second + p3.second) / 3

        // 绘制顶点到重心的连线
        canvas.drawLine(p1.first, p1.second, centroidX, centroidY, paint)
        canvas.drawLine(p2.first, p2.second, centroidX, centroidY, paint)
        canvas.drawLine(p3.first, p3.second, centroidX, centroidY, paint)

        val dynamicP1 =
            centroidX + (p1.first - centroidX) * (animatedUpRating / maxRating.toFloat()) to centroidY + (p1.second - centroidY) * (animatedUpRating / maxRating.toFloat())
        val dynamicP2 =
            centroidX + (p2.first - centroidX) * (animatedLeftRating / maxRating.toFloat()) to centroidY + (p2.second - centroidY) * (animatedLeftRating / maxRating.toFloat())
        val dynamicP3 =
            centroidX + (p3.first - centroidX) * (animatedRightRating / maxRating.toFloat()) to centroidY + (p3.second - centroidY) * (animatedRightRating / maxRating.toFloat())

        // 绘制内部动态三角形
        val ratingPath = Path().apply {
            moveTo(dynamicP1.first, dynamicP1.second)
            lineTo(dynamicP2.first, dynamicP2.second)
            lineTo(dynamicP3.first, dynamicP3.second)
            close()
        }
        canvas.drawPath(ratingPath, outerPaint)
        canvas.drawPath(ratingPath, fillPaint)

        // 绘制动态点上的空心圆
        canvas.drawCircle(dynamicP1.first, dynamicP1.second, circleRadius, circlePaint)
        canvas.drawCircle(dynamicP1.first, dynamicP1.second, circleRadius - context.dpToPx(1.5f), circleFillPaint)
        canvas.drawCircle(dynamicP2.first, dynamicP2.second, circleRadius, circlePaint)
        canvas.drawCircle(dynamicP2.first, dynamicP2.second, circleRadius - context.dpToPx(1.5f), circleFillPaint)
        canvas.drawCircle(dynamicP3.first, dynamicP3.second, circleRadius, circlePaint)
        canvas.drawCircle(dynamicP3.first, dynamicP3.second, circleRadius - context.dpToPx(1.5f), circleFillPaint)
    }
}
