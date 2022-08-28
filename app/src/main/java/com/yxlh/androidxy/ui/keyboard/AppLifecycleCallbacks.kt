package com.yxlh.androidxy.ui.keyboard

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 *@describe
 *@author zwl
 *@date on 2022/8/26
 */
class AppLifecycleCallbacks : ActivityLifecycleCallbacks {

    private var controller: WindowInsetsControllerCompat? = null

    var decorView: View? = null

    private val activityList by lazy { ArrayList<Activity>() }


    fun controller() = controller


    fun decorView() = decorView


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activityList.add(activity)
        decorView = activity?.window?.decorView
        decorView?.let {
            controller = WindowCompat.getInsetsController(activity.window, decorView!!)
        }
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    fun isVisible(type: Int): Boolean {
        decorView?.let {
            return ViewCompat.getRootWindowInsets(it)?.isVisible(type) == true
        }
        return false
    }

    override fun onActivityDestroyed(activity: Activity) {
        activityList.remove(activity)
        if (activityList.isEmpty()) {
            decorView = null
            controller = null
        }
    }
}