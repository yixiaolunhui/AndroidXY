package com.yxlh.androidxy.demo.ui.card

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

private var currentAnimatorSet: AnimatorSet? = null

fun View?.animateScale(scale: Float, anim: Boolean? = true) {
    // 如果当前动画正在运行，取消动画
    currentAnimatorSet?.cancel()

    // 设置缩放动画
    val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, scale)
    val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, scale)

    // 同时运行缩放动画
    currentAnimatorSet = AnimatorSet().apply {
        playTogether(scaleX, scaleY)
        duration = if(anim == true) 500 else 0
        start()
    }
}
