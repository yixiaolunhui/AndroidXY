package com.yxlh.permission.api

import android.content.Context

/**
 *@describe 权限操作接口
 *@author zwl
 *@date on 2022/10/8
 */
interface IPermissionApi {

    fun isGrant(context: Context, permission: String): Boolean

    fun isGrant(context: Context, permission: Array<String>): Boolean

    fun isGrant(context: Context, permission: List<String>): Boolean

    fun requestPermission(
        context: Context, permission: Array<String>,
        enterSetting: Boolean, callback: IPermissionCallBack
    )

    fun requestPermission(
        context: Context, permission: List<String>,
        enterSetting: Boolean, callback: IPermissionCallBack
    )

}