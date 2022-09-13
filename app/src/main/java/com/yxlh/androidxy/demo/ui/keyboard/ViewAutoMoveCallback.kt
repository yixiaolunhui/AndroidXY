package com.yxlh.androidxy.demo.ui.keyboard

import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat

/**
 *@author zwl
 *@date on 2022/8/26
 */
class ViewAutoMoveCallback constructor(dispatchMode: Int, var view: View) : WindowInsetsAnimationCompat.Callback(dispatchMode) {

    private var deferredInsets = false


    override fun onPrepare(animation: WindowInsetsAnimationCompat) {
        if (animation.typeMask and UiType.KEYBOARY !== 0) {
            deferredInsets = true
        }
    }

    override fun onProgress(insets: WindowInsetsCompat, runningAnimations: List<WindowInsetsAnimationCompat?>)
            : WindowInsetsCompat {
        if (deferredInsets && view != null) {
            val typesInset = insets.getInsets(UiType.KEYBOARY)
            val otherInset = insets.getInsets(UiType.ALL_BARS)
            val subtract = Insets.subtract(typesInset, otherInset)
            val diff = Insets.max(subtract, Insets.NONE)
            view.translationX = (diff.left - diff.right).toFloat()
            view.translationY = (diff.top - diff.bottom).toFloat()
        }
        return insets
    }

    override fun onEnd(animation: WindowInsetsAnimationCompat) {
        if (deferredInsets && animation.typeMask and UiType.KEYBOARY !== 0) {
            deferredInsets = false
        }
    }
}