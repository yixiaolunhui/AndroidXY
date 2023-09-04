package com.yxlh.permission

import android.content.Context
import android.util.Log
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
class XYPermission private constructor(context: Context) {

    private val TAG = "XYPermission"

    private var context: Context? = null

    private lateinit var permissions: Array<String>

    private var callBack: IPermissionCallBack? = null

    private val intercepts by lazy { InterceptHelper.builder() }

    private var permissionMode = PermissionImplMode.MODE_XX_PERMISSION


    init {
        this.context = context
        this.intercepts.getInterceptManager.clear()
    }

    companion object {
        fun with(context: Context): XYPermission {
            return XYPermission(context)
        }
    }

    fun mode(mode: PermissionImplMode): XYPermission {
        permissionMode = mode
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
            Log.e(TAG, "context can not be null")
            return
        }
        if (!isGrant(permissions)) {
            intercepts.setInterceptListener(object : InterceptListener {
                override fun proceed() {
                    requestPermission()
                }

                override fun interrupt() {
                    callBack?.onDenied(false)
                }

            }).execute()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        PermissionFactory.create(permissionMode)
            .requestPermission(context!!, permissions, true, object : IPermissionCallBack {
                override fun onGranted() {
                    callBack?.onGranted()
                }

                override fun onDenied(never: Boolean) {
                    callBack?.onDenied(never)
                }
            })
    }

    fun isGrant(permission: Array<String>): Boolean {
        if (context == null) {
            Log.e(TAG, "context can not be null")
            return false
        }
        return PermissionFactory.create(permissionMode)
            .isGrant(context!!, permission)
    }

}