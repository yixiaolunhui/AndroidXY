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
    private var circleRadius: Float = context.dpToPx(5f)
    private var padding: Float = 0f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val outerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val circleFillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    private val path = Path()
    private val ratingPath = Path()

    init {
        context.withStyledAttributes(attrs, R.styleable.TriangleRatingAnimView) {
            enableAnimation = getBoolean(R.styleable.TriangleRatingAnimView_enableAnimation, true)
            maxRating = getInt(R.styleable.TriangleRatingAnimView_maxRating, 5)
            strokeColor = getColor(R.styleable.TriangleRatingAnimView_strokeColor, Color.GRAY)
            strokeWidth = getDimension(R.styleable.TriangleRatingAnimView_strokeWidth, context.dpToPx(2f))
            ratingStrokeColor = getColor(R.styleable.TriangleRatingAnimView_ratingStrokeColor, Color.RED)
            ratingStrokeWidth = getDimension(R.styleable.TriangleRatingAnimView_ratingStrokeWidth, context.dpToPx(4f))
            circleRadius = getDimension(R.styleable.TriangleRatingAnimView_circleRadius, context.dpToPx(5f))
            upRating = getInt(R.styleable.TriangleRatingAnimView_upRating, 0)
            leftRating = getInt(R.styleable.TriangleRatingAnimView_leftRating, 0)
            rightRating = getInt(R.styleable.TriangleRatingAnimView_rightRating, 0)
        }

        padding = circleRadius + context.dpToPx(2f)

        paint.color = strokeColor
        paint.strokeWidth = strokeWidth

        outerPaint.color = ratingStrokeColor
        outerPaint.strokeWidth = ratingStrokeWidth

        fillPaint.color = ColorUtils.setAlphaComponent(ratingStrokeColor, (0.3 * 255).toInt())

        circlePaint.color = ratingStrokeColor
        circlePaint.strokeWidth = ratingStrokeWidth

        animatedUpRating = upRating
        animatedLeftRating = leftRating
        animatedRightRating = rightRating
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

        val p1 = floatArrayOf(width / 2, padding)
        val p2 = floatArrayOf(padding, height - padding)
        val p3 = floatArrayOf(width - padding, height - padding)

        // 绘制外部三角形
        path.reset()
        path.moveTo(p1[0], p1[1])
        path.lineTo(p2[0], p2[1])
        path.lineTo(p3[0], p3[1])
        path.close()
        canvas.drawPath(path, paint)

        val centroidX = (p1[0] + p2[0] + p3[0]) / 3
        val centroidY = (p1[1] + p2[1] + p3[1]) / 3

        // 绘制顶点到重心的连线
        canvas.drawLine(p1[0], p1[1], centroidX, centroidY, paint)
        canvas.drawLine(p2[0], p2[1], centroidX, centroidY, paint)
        canvas.drawLine(p3[0], p3[1], centroidX, centroidY, paint)

        val dynamicP1 = floatArrayOf(
            centroidX + (p1[0] - centroidX) * (animatedUpRating / maxRating.toFloat()),
            centroidY + (p1[1] - centroidY) * (animatedUpRating / maxRating.toFloat())
        )
        val dynamicP2 = floatArrayOf(
            centroidX + (p2[0] - centroidX) * (animatedLeftRating / maxRating.toFloat()),
            centroidY + (p2[1] - centroidY) * (animatedLeftRating / maxRating.toFloat())
        )
        val dynamicP3 = floatArrayOf(
            centroidX + (p3[0] - centroidX) * (animatedRightRating / maxRating.toFloat()),
            centroidY + (p3[1] - centroidY) * (animatedRightRating / maxRating.toFloat())
        )

        // 绘制内部动态三角形
        ratingPath.reset()
        ratingPath.moveTo(dynamicP1[0], dynamicP1[1])
        ratingPath.lineTo(dynamicP2[0], dynamicP2[1])
        ratingPath.lineTo(dynamicP3[0], dynamicP3[1])
        ratingPath.close()
        canvas.drawPath(ratingPath, outerPaint)
        canvas.drawPath(ratingPath, fillPaint)

        // 绘制动态点上的空心圆
        if (upRating != 0 || leftRating != 0 || rightRating != 0) {
            drawCircle(canvas, dynamicP1)
            drawCircle(canvas, dynamicP2)
            drawCircle(canvas, dynamicP3)
        }
    }

    private fun drawCircle(canvas: Canvas, point: FloatArray) {
        canvas.drawCircle(point[0], point[1], circleRadius, circlePaint)
        canvas.drawCircle(point[0], point[1], circleRadius - context.dpToPx(1.5f), circleFillPaint)
    }
}
