package com.yxlh.permission.intercept


/**
 *@author zwl
 *@date on 2022/9/5
 */
interface InterceptorApi {

    fun addIntercept(intercept: Interceptor)

    /**
     * 继续
     */
    fun proceed()

    /**
     * 中断
     */
    fun interrupt()
}