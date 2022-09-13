package com.yxlh.androidxy.demo.function.chain

/**
 *@author zwl
 *@date on 2022/9/5
 */
interface Chain {

    fun addChain(interceptor: ChainInterceptor)

    fun addChain(index: Int, interceptor: ChainInterceptor)

    fun proceed()
}