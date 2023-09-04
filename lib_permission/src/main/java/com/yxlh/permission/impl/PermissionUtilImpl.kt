package com.yxlh.permission.impl

import android.content.Context
import com.blankj.utilcode.util.PermissionUtils
import com.yxlh.permission.api.IPermissionApi
import com.yxlh.permission.api.IPermissionCallBack

/**
 *@describe PermissionUtil实现
 *@author zwl
 *@date on 2022/10/9
 */
class PermissionUtilImpl : IPermissionApi {
    override fun isGrant(context: Context, permission: String) = PermissionUtils.isGranted(permission)

    override fun isGrant(context: Context, permission: Array<String>) = PermissionUtils.isGranted(*permission)

    override fun isGrant(context: Context, permission: List<String>) = PermissionUtils.isGranted(*permission.toTypedArray())

    override fun requestPermission(
        context: Context, permission: Array<String>,
        enterSetting: Boolean,
        callback: IPermissionCallBack
    ) {
        requestPermission(context, permission.toList(), enterSetting, callback)
    }

    override fun requestPermission(
        context: Context, permission: List<String>,
        enterSetting: Boolean,
        callback: IPermissionCallBack
    ) {
        PermissionUtils.permission(*permission.toTypedArray())
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    callback.onGranted()
                }

                override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {
                    callback.onDenied(deniedForever.size > 0)
                }
            })
            .request()
    }


}