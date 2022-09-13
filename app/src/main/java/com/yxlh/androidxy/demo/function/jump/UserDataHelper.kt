package com.yxlh.androidxy.demo.function.jump

/**
 *@describe 测试用户信息
 *@author zwl
 *@date on 2022/9/7
 */
object UserDataHelper {


    var isLogin = false

    var isAuth = false

    fun clear() {
        this.isLogin = false
        this.isAuth = false
    }
}