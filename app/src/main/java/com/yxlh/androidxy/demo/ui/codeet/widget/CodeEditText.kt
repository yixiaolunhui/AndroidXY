package com.yxlh.androidxy.demo.ui.codeet.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.yxlh.androidxy.R
import com.yxlh.androidxy.utils.DensityUtil


/**
 *@describe 验证码
 *@author zwl
 *@date on 2022/9/11
 */
class CodeEditText @JvmOverloads constructor(context: Context, var attrs: AttributeSet, var defStyleAttr: Int = 0) :
    AppCompatEditText(context, attrs, defStyleAttr) {

    //code模式
    private var codeMode: Int = 0

    //code样式
    private var codeStyle: Int = 0

    //code背景色
    private var codeBgColor: Int = Color.WHITE

    //边框宽度
    private var borderWidth: Float = 1.0f

    //边框默认颜色
    private var borderColor: Int = Color.GRAY

    //边框选中颜色
    private var borderSelectColor: Int = Color.GRAY

    //边框圆角
    private var borderRadius: Float = 3.0f

    //内容颜色（密码或文字）
    private var codeContentColor: Int = Color.GRAY

    //内容大小（密码或文字）
    private var codeContentSize: Float = 0f

    //单个Item宽度
    private var codeItemWidth = -1

    //最大长度
    private val maxLength by lazy { CodeHelper.maxLength(this) }

    //距离左右间隙空间  默认是0
    private var space = 0

    //当前输入索引
    private var currentIndex = -1

    //是否获取焦点
    private var isCodeFocused = false

    //Item之间的间隙（只有方框、横线、圆圈生效）
    private var codeItemSpace = 0f


    //默认Drawable
    private val defaultDrawable by lazy {
        GradientDrawable().also {
            it.setStroke(borderWidth.toInt(), borderColor)
            it.setColor(codeBgColor)
            it.cornerRadius = borderRadius
        }
    }

    //当前光标Drawable
    private val currentDrawable by lazy {
        GradientDrawable().also {
            it.setStroke(borderWidth.toInt(), borderSelectColor)
            it.setColor(codeBgColor)
        }
    }

    //默认样式 Bitmap
    private var defaultBitmap: Bitmap? = null

    //当前选中的Bitmap
    private var currentBitmap: Bitmap? = null


    //划线画笔
    private val mLinePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.color = borderColor
            it.style = Paint.Style.STROKE
            it.strokeWidth = borderWidth
        }
    }

    //文字画笔
    private val mTextPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.style = Paint.Style.FILL
            it.color = codeContentColor
            it.textSize = codeContentSize
        }

    }

    init {
        initSetting()
        initAttrs()
    }


    private fun initAttrs() {
        val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CodeEditText)
        codeMode = obtainStyledAttributes.getInt(R.styleable.CodeEditText_code_mode, 0)
        codeStyle = obtainStyledAttributes.getInt(R.styleable.CodeEditText_code_style, 0)
        borderWidth = obtainStyledAttributes.getDimension(R.styleable.CodeEditText_code_border_width, DensityUtil.dip2px(context, 1.0f))
        borderColor = obtainStyledAttributes.getColor(R.styleable.CodeEditText_code_border_color, Color.GRAY)
        borderSelectColor = obtainStyledAttributes.getColor(R.styleable.CodeEditText_code_border_select_color, Color.GRAY)
        borderRadius = obtainStyledAttributes.getDimension(R.styleable.CodeEditText_code_border_radius, 0f)
        codeBgColor = obtainStyledAttributes.getColor(R.styleable.CodeEditText_code_bg_color, Color.WHITE)
        codeItemWidth = obtainStyledAttributes.getDimension(R.styleable.CodeEditText_code_item_width, -1f).toInt()
        codeItemSpace = obtainStyledAttributes.getDimension(R.styleable.CodeEditText_code_item_space, DensityUtil.dip2px(context, 16f))
        if (codeStyle == 0) codeItemSpace = 0f
        codeContentColor = obtainStyledAttributes.getColor(R.styleable.CodeEditText_code_content_color, Color.GRAY)
        codeContentSize = obtainStyledAttributes.getDimension(R.styleable.CodeEditText_code_content_size, DensityUtil.dip2px(context, 16f))
        obtainStyledAttributes.recycle()
    }

    private fun initSetting() {
        setTextColor(Color.TRANSPARENT)
        isFocusableInTouchMode = true
        isCursorVisible = false
        setOnLongClickListener { true }
    }


    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        isCodeFocused = focused
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //当前索引（待输入的光标位置）
        currentIndex = text?.length ?: 0
        //Item宽度（这里判断如果设置了宽度并且合理就使用当前设置的宽度，否则平均计算）
        codeItemWidth = if (codeItemWidth != -1 && (codeItemWidth * maxLength + codeItemSpace * (maxLength - 1)) <= measuredWidth) {
            codeItemWidth
        } else {
            ((measuredWidth - codeItemSpace * (maxLength - 1)) / maxLength).toInt()
        }

        //计算左右间距大小
        space = ((measuredWidth - codeItemWidth * maxLength - codeItemSpace * (maxLength - 1)) / 2).toInt()

        //绘制Code样式
        when (codeStyle) {
            //表格
            0 -> {
                drawFormCode(canvas)
            }
            //方块
            1 -> {
                drawRectangleCode(canvas)
            }
            //横线
            2 -> {
                drawLineCode(canvas)
            }
            //圆形
            3 -> {
                drawCircleCode(canvas)
            }
        }

        //绘制文字
        drawContentText(canvas)
    }

    /**
     * 绘制内容
     * https://blog.csdn.net/weixin_42169702/article/details/107858979
     */
    private fun drawContentText(canvas: Canvas) {
        val textStr = text.toString()
        for (i in 0 until maxLength) {
            if (textStr.isNotEmpty() && i < textStr.length) {
                when (codeMode) {
                    //文字
                    0 -> {
                        val code: String = textStr[i].toString()
                        val textWidth: Float = mTextPaint.measureText(code)
                        val textHeight: Float = CodeHelper.getTextHeight(code, mTextPaint)
                        val x: Float = space + codeItemWidth * i + codeItemSpace * i + (codeItemWidth - textWidth) / 2
                        val y: Float = (measuredHeight + textHeight) / 2f
                        canvas.drawText(code, x, y, mTextPaint)
                    }
                }
            }
        }


    }

    /**
     * 表格code
     */
    private fun drawFormCode(canvas: Canvas) {
        //绘制表格边框
        defaultDrawable.setBounds(space, 0, measuredWidth - space, measuredHeight)
        defaultBitmap = CodeHelper.drawableToBitmap(defaultDrawable, measuredWidth - 2 * space, measuredHeight)
        canvas.drawBitmap(defaultBitmap!!, space.toFloat(), 0f, mLinePaint)


        //绘制表格中间分割线
        for (i in 1 until maxLength) {
            val startX = space + codeItemWidth * i + codeItemSpace * i
            val startY = 0f
            val stopY = measuredHeight
            canvas.drawLine(startX, startY, startX, stopY.toFloat(), mLinePaint)
        }


        //绘制当前位置边框
        for (i in 0 until maxLength) {
            if (currentIndex != -1 && currentIndex == i && isCodeFocused) {
                when (i) {
                    0 -> {
                        val radii = floatArrayOf(borderRadius, borderRadius, 0f, 0f, 0f, 0f, borderRadius, borderRadius)
                        currentDrawable.cornerRadii = radii
                        currentBitmap =
                            CodeHelper.drawableToBitmap(currentDrawable, (codeItemWidth + borderWidth / 2).toInt(), measuredHeight)
                    }
                    maxLength - 1 -> {
                        val radii = floatArrayOf(0f, 0f, borderRadius, borderRadius, borderRadius, borderRadius, 0f, 0f)
                        currentDrawable.cornerRadii = radii
                        currentBitmap =
                            CodeHelper.drawableToBitmap(currentDrawable, (codeItemWidth + borderWidth / 2 + codeItemSpace).toInt(), measuredHeight)
                    }
                    else -> {
                        val radii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
                        currentDrawable.cornerRadii = radii
                        currentBitmap = CodeHelper.drawableToBitmap(currentDrawable, (codeItemWidth + borderWidth).toInt(), measuredHeight)
                    }
                }
                val left = if (i == 0) (space + codeItemWidth * i) else ((space + codeItemWidth * i + codeItemSpace * i) - borderWidth / 2)
                canvas.drawBitmap(currentBitmap!!, left.toFloat(), 0f, mLinePaint)
            }
        }
    }


    /**
     * 方块 code
     */
    private fun drawRectangleCode(canvas: Canvas) {
        defaultDrawable.cornerRadius = borderRadius
        defaultBitmap = CodeHelper.drawableToBitmap(defaultDrawable, codeItemWidth, measuredHeight)

        currentDrawable.cornerRadius = borderRadius
        currentBitmap = CodeHelper.drawableToBitmap(currentDrawable, codeItemWidth, measuredHeight)

        for (i in 0 until maxLength) {
            val left = if (i == 0) {
                space + i * codeItemWidth
            } else {
                space + i * codeItemWidth + codeItemSpace * i
            }
            //当前光标样式
            if (currentIndex != -1 && currentIndex == i && isCodeFocused) {
                canvas.drawBitmap(currentBitmap!!, left.toFloat(), 0f, mLinePaint)
            }
            //默认样式
            else {
                canvas.drawBitmap(defaultBitmap!!, left.toFloat(), 0f, mLinePaint)
            }
        }

    }

    /**
     * 横线 code
     */
    private fun drawLineCode(canvas: Canvas) {
        for (i in 0 until maxLength) {
            //当前选中状态
            if (currentIndex == i && isCodeFocused) {
                mLinePaint.color = borderSelectColor
            }
            //默认状态
            else {
                mLinePaint.color = borderColor
            }
            val startX: Float = space + codeItemWidth * i + codeItemSpace * i
            val startY: Float = measuredHeight - borderWidth
            val stopX: Float = startX + codeItemWidth
            val stopY: Float = startY
            canvas.drawLine(startX, startY, stopX, stopY, mLinePaint)
        }

    }

    /**
     * 圆形 code
     */
    private fun drawCircleCode(canvas: Canvas) {
        for (i in 0 until maxLength) {
            //当前绘制的圆圈的左x轴坐标
            var left: Float = if (i == 0) {
                (space + i * codeItemWidth).toFloat()
            } else {
                space + i * codeItemWidth + codeItemSpace * i
            }
            //圆心坐标
            val cx: Float = left + codeItemWidth / 2f
            val cy: Float = measuredHeight / 2f
            //圆形半径
            val radius: Float = codeItemWidth / 5f
            //默认样式
            if (i >= currentIndex) {
                canvas.drawCircle(cx, cy, radius, mLinePaint.apply { style = Paint.Style.FILL })
            }
        }
    }


}