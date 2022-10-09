package com.yxlh.permission.impl

import com.yxlh.permission.api.IPermissionApi

/**
 *@describe 权限工厂
 *@author zwl
 *@date on 2022/10/8
 */
object PermissionFactory {

    fun create(mode: PermissionImplMode): IPermissionApi {
        return when (mode) {
            PermissionImplMode.MODE_XX_PERMISSION -> {
                XXPermissionImpl()
            }
            PermissionImplMode.MODE_PERMISSION_UTILS -> {
                PermissionUtilImpl()
            }
        }
    }
}


