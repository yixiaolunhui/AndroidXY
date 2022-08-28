package com.yxlh.androidxy.ui.keyboard

import android.app.Application
import android.view.View
import android.view.WindowInsetsAnimation.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat

/**
 *@author zwl
 *@date on 2022/8/26
 */
object KeyboardHelper {

    private val appLifecycleCallbacks by lazy { AppLifecycleCallbacks() }

    fun register(app: Application) {
        app.registerActivityLifecycleCallbacks(appLifecycleCallbacks)
    }

    fun setKeyBoardListener(keyboardListener: KeyboardListener) {
        var appWindowInsetsCallback = AppWindowInsetsCallback(DISPATCH_MODE_CONTINUE_ON_SUBTREE, keyboardListener)
        appLifecycleCallbacks.decorView()?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it, appWindowInsetsCallback)
            ViewCompat.setWindowInsetsAnimationCallback(it, appWindowInsetsCallback)
        }
    }


    fun setAutoMoveView(vararg views: View) {
        for (view in views) {
            setAutoMoveView(view)
        }
    }

    fun setAutoMoveView(view: View) {
        val viewAutoMoveCallback = ViewAutoMoveCallback(WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE, view!!)
        ViewCompat.setWindowInsetsAnimationCallback(view, viewAutoMoveCallback)
    }


    fun showOrHideKeyBoard(editText: EditText) {
        val visible: Boolean = appLifecycleCallbacks.isVisible(UiType.KEYBOARY)
        if (visible) {
            appLifecycleCallbacks.controller()?.hide(UiType.KEYBOARY)
        } else {
            editText.requestFocus()
            appLifecycleCallbacks.controller()?.show(UiType.KEYBOARY)
        }
    }

}
