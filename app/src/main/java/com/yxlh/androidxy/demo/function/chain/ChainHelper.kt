package com.yxlh.androidxy.demo.function.chain

/**
 *@author zwl
 *@date on 2022/9/5
 */
object ChainHelper {

    fun builder(): Builder {
        return Builder()
    }

    class Builder {
        private val chainManager by lazy { ChainManager() }

        fun addChain(interceptor: ChainInterceptor): Builder {
            chainManager.addChain(interceptor)
            return this
        }

        fun addChain(index: Int, interceptor: ChainInterceptor): Builder {
            chainManager.addChain(index, interceptor)
            return this
        }


        fun setChainStatusListener(chainStatusListener: ChainStatusListener): Builder {
            chainManager.setChainStatusListener(chainStatusListener)
            return this
        }


        val getChainManager: ChainManager
            get() = chainManager


        fun execute() {
            if (chainManager != null && chainManager.getChainCount() > 0) {
                chainManager.proceed()
            }
        }


    }

}