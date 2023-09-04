package com.yxlh.permission.intercept

/**
 *@author zwl
 *@date on 2022/9/5
 */
object InterceptHelper {

    fun builder(): Builder {
        return Builder()
    }

    class Builder {
        private val manager by lazy { InterceptManager() }

        fun addIntercept(interceptor: Interceptor): Builder {
            manager.addIntercept(interceptor)
            return this
        }


        fun setInterceptListener(chainStatusListener: InterceptListener): Builder {
            manager.setInterceptListener(chainStatusListener)
            return this
        }

        val getInterceptManager: InterceptManager
            get() = manager


        fun execute() {
            if (manager.getChainCount() >= 0) {
                manager.proceed()
            }
        }


    }

}