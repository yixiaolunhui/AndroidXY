package com.yxlh.permission.intercept

import java.util.*

/**
 *@author zwl
 *@date on 2022/9/5
 */
class InterceptManager : InterceptorApi {

    private val chains: MutableList<Interceptor> = LinkedList<Interceptor>()

    private var index = 0

    private var interceptListener: InterceptListener? = null

    fun setInterceptListener(interceptListener: InterceptListener) {
        this.interceptListener = interceptListener
    }

    override fun addIntercept(interceptor: Interceptor) {
        chains.add(interceptor)
    }

    fun getChainCount() = chains.size

    override fun proceed() {
        val isEnd = index >= chains.size
        if (isEnd) {
            interceptListener?.proceed()
            clear()
            return
        }
        chains[index++].intercept(this)
    }

    override fun interrupt() {
        interceptListener?.interrupt()
    }

    fun clear() {
        chains?.clear()
        index = 0
    }

}

