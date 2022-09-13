package com.yxlh.androidxy.demo.function.chain

import java.util.*

/**
 *@author zwl
 *@date on 2022/9/5
 */
class ChainManager : Chain {

    //任务链
    private val chains: MutableList<ChainInterceptor> = LinkedList<ChainInterceptor>()

    //任务链 索引
    private var index = 0

    //链条执行状态
    private var statusListener: ChainStatusListener? = null


    /**
     * 设置链条执行状态
     */
    fun setChainStatusListener(chainStatusListener: ChainStatusListener) {
        this.statusListener = chainStatusListener
    }

    /**
     * 添加链条
     */
    override fun addChain(interceptor: ChainInterceptor) {
        chains.add(interceptor)
    }

    /**
     * 添加链条
     */
    override fun addChain(index: Int, interceptor: ChainInterceptor) {
        chains.add(index, interceptor)
    }


    /**
     * 链条数量
     */
    fun getChainCount() = chains.size

    /**
     * 链条执行
     */
    override fun proceed() {
        val isChainEnd = index >= chains.size
        statusListener?.onStatusChange(isChainEnd)
        if (isChainEnd) {
            return
        }
        chains[index++].intercept(this)
    }


    /**
     * 清空链条
     */
    fun clear() {
        chains?.clear()
        index = 0
    }

}

