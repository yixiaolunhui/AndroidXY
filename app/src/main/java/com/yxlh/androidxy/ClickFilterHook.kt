package com.yxlh.androidxy

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
class ClickFilterHook {

    @Around("execution(* android.app.Activity.finish(..))")
    @Throws(Throwable::class)
    fun aroundFinish(joinPoint: ProceedingJoinPoint) {
        Log.d(TAG, "aroundFinish3: " + "可以快速点击")
        val activity = joinPoint.`this` as Activity
        // 显示确认对话框
        AlertDialog.Builder(activity).setTitle("确认退出").setMessage("你确定要退出吗？").setPositiveButton("确定") { _, _ ->
            try {
                // 用户点击确认后执行 finish
                joinPoint.proceed()
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }.setNegativeButton("取消", null).show()
    }


    companion object {
        private const val TAG = "ClickFilterHook"
    }
}