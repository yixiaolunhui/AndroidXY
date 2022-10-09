package com.yxlh.permission.impl

import android.content.Context
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

    override fun requestPermission(context: Context, permission: Array<String>, type: Int, enterSetting: Boolean, callback: IPermissionCallBack) {
        requestPermission(context, permission.toList(), type, enterSetting, callback)
    }

    override fun requestPermission(context: Context, permission: List<String>, type: Int, enterSetting: Boolean, callback: IPermissionCallBack) {
        XXPermissions.with(context)
            .permission(permission)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                    callback?.onGranted(permissions, all)
                }

                override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                    super.onDenied(permissions, never)
                    callback?.onDenied(permissions, never)
                }
            })
    }

}