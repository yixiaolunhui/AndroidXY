package com.yxlh.androidxy.ui.arc

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.yxlh.androidxy.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 *@describe 圆弧
 *@author zwl
 *@date on 2022/8/23
 */
class ArcProgressView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    private var arcBgColor = Color.BLUE
    private var arcProgressColor = Color.RED
    private var arcSize = 12F
    private var maxProgress = 100
    private var progress = 60

    //圆弧背景Paint
    private val bgPaint by lazy {
        Paint().apply {
            //背景色
            color = arcBgColor
            //笔画宽度
            strokeWidth = arcSize
            //绘制样式
            style = Paint.Style.STROKE
            //圆角
            strokeCap = Paint.Cap.ROUND
            //抗锯齿
            isAntiAlias = true
            //防抖动
            isDither = true
        }
    }

    //圆弧进度Paint
    private val progressPaint by lazy {
        Paint().apply {
            //背景色
            color = arcProgressColor
            //笔画宽度
            strokeWidth = arcSize
            //绘制样式
            style = Paint.Style.STROKE
            //圆角
            strokeCap = Paint.Cap.ROUND
            //抗锯齿
            isAntiAlias = true
            //防抖动
            isDither = true
        }
    }

    //指针Paint
    private val pointerPaint by lazy {
        Paint().apply {
            //背景色
            color = Color.BLACK
            //笔画宽度
            strokeWidth = 20f
            //绘制样式
            style = Paint.Style.STROKE
            //圆角
            strokeCap = Paint.Cap.ROUND
            //抗锯齿
            isAntiAlias = true
            //防抖动
            isDither = true
        }
    }

    init {
        //获取配置参数
        context?.obtainStyledAttributes(attrs, R.styleable.ArcProgressView)?.apply {
            arcBgColor = getColor(R.styleable.ArcProgressView_arcBgColor, arcBgColor)
            arcProgressColor = getColor(R.styleable.ArcProgressView_arcProgressColor, arcProgressColor)
            arcSize = getDimension(R.styleable.ArcProgressView_arcSize, arcSize)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //取宽高最小的值为圆弧的尺寸，保证正方形内切圆
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        var size = width.coerceAtMost(height)
        setMeasuredDimension(size, size)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //正方形RectF
        val rectF = RectF(arcSize / 2, arcSize / 2, measuredWidth - arcSize / 2, measuredHeight - arcSize / 2)

        //绘制圆弧
        var startAngle = 135f
        var sweepAngle = 270f

        //绘制背景圆弧
        canvas?.drawArc(rectF, startAngle, sweepAngle, false, bgPaint)

        //绘制进度圆弧
        val percentage = 1.0f * progress / maxProgress
        canvas?.drawArc(rectF, startAngle, percentage * sweepAngle, false, progressPaint)

        //绘制中心小圆
        canvas?.drawCircle(1.0f * measuredWidth / 2, 1.0f * measuredHeight / 2, 5.0f, pointerPaint)

        //绘制指针
        var l = measuredWidth / 2 * 0.75
        canvas?.drawLine(
            1.0f * measuredWidth / 2, 1.0f * measuredHeight / 2,
            (measuredWidth / 2 + l * cos(degToRad(startAngle + percentage * sweepAngle))).toFloat(),
            (measuredWidth / 2 + l * sin(degToRad(startAngle + percentage * sweepAngle))).toFloat(),
            pointerPaint
        )
    }

    ///角度转换为弧度
    private fun degToRad(angle: Float) = angle * (PI / 180.0);


    /**
     * 设置当前进度
     */
    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    /**
     * 设置最大进度
     */
    fun setMaxProgress(maxProgress: Int) {
        this.maxProgress = maxProgress
    }


}