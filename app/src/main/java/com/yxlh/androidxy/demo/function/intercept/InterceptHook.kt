package com.yxlh.androidxy.demo.function.intercept

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

@Aspect
class InterceptHook {

    companion object {
        private const val TAG = "InterceptHook"
    }

    @Before("execution(* android.app.Activity.onStart(..))")
    fun beforeStart(joinPoint: JoinPoint) {
        Log.d(TAG, "beforeStart: "+"可以快速点击")
    }


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

}