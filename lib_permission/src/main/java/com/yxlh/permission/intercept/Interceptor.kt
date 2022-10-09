package com.yxlh.permission.intercept

/**
 *@author zwl
 *@date on 2022/9/5
 */
interface Interceptor {

    fun intercept(api: InterceptorApi)

}