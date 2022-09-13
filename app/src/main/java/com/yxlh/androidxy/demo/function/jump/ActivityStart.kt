package com.yxlh.androidxy.demo.function.jump

import android.content.Context
import android.content.Intent
import com.yxlh.androidxy.demo.function.chain.ChainHelper
import com.yxlh.androidxy.demo.function.chain.ChainInterceptor
import com.yxlh.androidxy.demo.function.chain.ChainStatusListener

/**
 *@author zwl
 *@date on 2022/9/7
 */
object ActivityStart {

    fun with(context: Context): Builder {
        return Builder(context)
    }


    class Builder constructor(private var context: Context) {

        private val chainHelper by lazy { ChainHelper.builder() }

        fun addIntercept(interceptor: ChainInterceptor): Builder {
            chainHelper.addChain(interceptor)
            return this
        }

        fun go(cls: Class<*>) {
            var intent = Intent(context, cls)
            if (chainHelper.getChainManager.getChainCount() > 0) {
                chainHelper.setChainStatusListener(object : ChainStatusListener {
                    override fun onStatusChange(isChainEnd: Boolean) {
                        if(isChainEnd){
                            context.startActivity(intent)
                        }
                    }
                }).execute()
            } else {
                context.startActivity(intent)
            }

        }

    }
}