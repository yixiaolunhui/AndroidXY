package com.yxlh.androidxy.ui.keyboard

import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat


object UiType {
    val KEYBOARY = WindowInsetsCompat.Type.ime()
    val ALL_BARS = WindowInsetsCompat.Type.systemBars()
    val STATUS_BAR = WindowInsetsCompat.Type.statusBars()
    val NAVIGATION_BAR = WindowInsetsCompat.Type.navigationBars()
}

/**
 *@author zwl
 *@date on 2022/8/26
 */
class AppWindowInsetsCallback constructor(
    dispatchMode: Int,
    var keyboardListener: KeyboardListener
) : WindowInsetsAnimationCompat.Callback(dispatchMode), OnApplyWindowInsetsListener {

    var view: View? = null

    var insets: WindowInsetsCompat? = null

    var deferredInsets: Boolean? = false


    override fun onPrepare(animation: WindowInsetsAnimationCompat) {
        if ((animation.typeMask and UiType.KEYBOARY) != 0) {
            deferredInsets = true
            keyboardListener?.onKeyBoardStart()
        }
    }

    override fun onEnd(animation: WindowInsetsAnimationCompat) {
        if (deferredInsets!! && animation.typeMask and UiType.KEYBOARY !== 0) {
            deferredInsets = false
            keyboardListener.onKeyBoardEnd()
            if (insets != null && view != null) {
                ViewCompat.dispatchApplyWindowInsets(view!!, insets!!)
            }
        }
    }

    override fun onProgress(
        insets: WindowInsetsCompat,
        runningAnimations: MutableList<WindowInsetsAnimationCompat>
    ): WindowInsetsCompat {
        if (deferredInsets == true) {
            val typesInset = insets.getInsets(UiType.KEYBOARY)
            val otherInset = insets.getInsets(UiType.ALL_BARS)
            val subtract = Insets.subtract(typesInset, otherInset)
            val diff = Insets.max(subtract, Insets.NONE)
            keyboardListener.onKeyBoardChange(diff.bottom)
        }
        return insets
    }

    override fun onApplyWindowInsets(v: View, insets: WindowInsetsCompat)
            : WindowInsetsCompat {
        this.view = view
        this.insets = insets
        return WindowInsetsCompat.CONSUMED
    }
}