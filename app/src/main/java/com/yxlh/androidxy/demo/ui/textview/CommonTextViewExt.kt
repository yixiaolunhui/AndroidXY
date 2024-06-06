package com.yxlh.androidxy.demo.ui.textview

import android.widget.TextView

/**
 * 功能描述:
 * @author weiyf
 * @date 2023/9/18
 */
/**
 * 解决TextView中包含英文字符串(包括数字也一样)且当前行剩余空间容不下时自动整体换行的问题
 */
fun TextView.getNotBreakByWordText(width: Int): CharSequence {
    // 原始文本
    val originalText = text.toString()
    // 获取TextView的Paint 用于测量字符宽度
    val textPaint = paint
    // TextView的可用宽度
    val canUseWidth = (width - paddingLeft - paddingRight).toFloat()
    if (canUseWidth <= 0) {
        return originalText
    }
    // 将原始文本按行拆分
    val originalTextLines = originalText.replace("\r".toRegex(), "")
        .split("\n".toRegex())
        .toTypedArray()
    val newTextBuilder = StringBuilder()
    originalTextLines.forEachIndexed { index, it ->
        if (textPaint.measureText(it) <= canUseWidth) {
            // 文本内容小于TextView宽度，即不换行，不作处理
            newTextBuilder.append(it)
        } else {
            // 如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
            var lineWidth = 0f
            it.forEach {
                val charWith = textPaint.measureText(it.toString())
                lineWidth += charWith
                if (lineWidth <= canUseWidth) {
                    newTextBuilder.append(it)
                } else {
                    newTextBuilder.append("\n${it}")
                    lineWidth = charWith
                }
            }
        }
        // 如果不是最后一行，则需要增加换行符
        if (index < originalTextLines.lastIndex) {
            newTextBuilder.append("\n")
        }
    }
    return newTextBuilder
}