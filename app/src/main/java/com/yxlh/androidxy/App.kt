package com.yxlh.androidxy

import android.app.Application
import com.yxlh.androidxy.ui.keyboard.KeyboardHelper

/**
 *@describe TODO
 *@author zwl
 *@date on 2022/8/26
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        KeyboardHelper.register(this)
    }
}