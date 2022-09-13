package com.yxlh.androidxy.utils

import android.content.Context

/**
 *@author zwl
 *@date on 2022/9/2
 */
object DensityUtil {
    /**
     * dp-px
     */
    fun dip2px(context: Context, dpValue: Float): Float {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f)
    }

    /**
     * px-dp
     */
    fun px2dip(context: Context, pxValue: Float): Float {
        val scale: Float = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f)
    }
}