package com.yxlh.permission.impl

import android.content.Context
import android.util.Log
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.yxlh.permission.api.IPermissionApi
import com.yxlh.permission.api.IPermissionCallBack

/**
 *@describe XXPermissions实现
 *@author zwl
 *@date on 2022/10/8
 */
class XXPermissionImpl : IPermissionApi {

    override fun isGrant(context: Context, permission: String) = XXPermissions.isGranted(context, permission)

    override fun isGrant(context: Context, permission: Array<String>) = XXPermissions.isGranted(context, permission)

    override fun isGrant(context: Context, permission: List<String>) = XXPermissions.isGranted(context, permission)

    override fun requestPermission(context: Context, permission: Array<String>, enterSetting: Boolean, callback: IPermissionCallBack) {
        requestPermission(context, permission.toList(), enterSetting, callback)
    }

    override fun requestPermission(context: Context, permission: List<String>, enterSetting: Boolean, callback: IPermissionCallBack) {
        XXPermissions.with(context)
            .permission(permission)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                    if (all) {
                        callback.onGranted()
                    }
                }

                override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                    super.onDenied(permissions, never)
                    callback.onDenied(never)
                }
            })
    }

}