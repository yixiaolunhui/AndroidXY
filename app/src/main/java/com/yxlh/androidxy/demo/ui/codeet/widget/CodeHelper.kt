package com.yxlh.androidxy.demo.ui.codeet.widget

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.widget.EditText
import java.lang.reflect.Field

/**
 *@describe Code帮助类
 *@author zwl
 *@date on 2022/9/13
 */
object CodeHelper {

    /**
     * drawable->Bitmap
     */
    fun drawableToBitmap(drawable: Drawable, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 获取字符串文字宽度
     */
    fun getTextWidth(str: String, paint: Paint): Float {
        val rect = Rect()
        paint.getTextBounds(str, 0, str.length, rect)
        return rect.width().toFloat()
    }

    /**
     * 获取字符串文字高度
     */
    fun getTextHeight(str: String, paint: Paint): Float {
        val rect = Rect()
        paint.getTextBounds(str, 0, str.length, rect)
        return rect.height().toFloat()
    }

    /**
     * 获取最大长度
     */
    fun maxLength(et: EditText): Int {
        var length = 0
        try {
            val inputFilters = et.filters
            for (filter in inputFilters) {
                val c: Class<*> = filter.javaClass
                if (c.name == "android.text.InputFilter\$LengthFilter") {
                    val f: Array<Field> = c.declaredFields
                    for (field in f) {
                        if (field.name.equals("mMax")) {
                            field.isAccessible = true
                            length = field.get(filter) as Int
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return length
    }

}