package com.yxlh.permission

import android.content.Context
import com.yxlh.permission.api.IPermissionCallBack
import com.yxlh.permission.impl.PermissionFactory
import com.yxlh.permission.impl.PermissionImplMode
import com.yxlh.permission.intercept.InterceptHelper
import com.yxlh.permission.intercept.InterceptListener
import com.yxlh.permission.intercept.Interceptor

/**
 *@author zwl
 *@date on 2022/10/8
 */
object XYPermission {

    private var context: Context? = null

    private var permissionMode = PermissionImplMode.MODE_XX_PERMISSION;

    private lateinit var permissions: Array<String>

    private var callBack: IPermissionCallBack? = null

    private val intercepts by lazy { InterceptHelper.builder() }

    fun with(context: Context): XYPermission {
        this.context = context
        this.intercepts?.getInterceptManager?.clear()
        return this
    }

    fun intercept(intercept: Interceptor): XYPermission {
        intercepts.addIntercept(intercept)
        return this
    }

    fun permissions(permissions: Array<String>): XYPermission {
        this.permissions = permissions
        return this
    }

    fun callBack(callBack: IPermissionCallBack): XYPermission {
        this.callBack = callBack
        return this
    }

    fun request() {
        if (context == null) {
            throw  RuntimeException("context can not be null")
        }
        intercepts.setInterceptListener(object : InterceptListener {
            override fun proceed() {
                PermissionFactory.create(permissionMode)
                    .requestPermission(context!!, permissions, 11, true, object : IPermissionCallBack {
                        override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                            callBack?.onGranted(permissions, all)
                        }

                        override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                            callBack?.onDenied(permissions, never)
                        }
                    })
            }

            override fun interrupt() {
                callBack?.onDenied(permissions.toMutableList(), false)
            }

        }).execute()

    }


    fun isGrant(context: Context, permission: Array<String>): Boolean {
        return PermissionFactory.create(permissionMode)
            .isGrant(context, permission)
    }

}