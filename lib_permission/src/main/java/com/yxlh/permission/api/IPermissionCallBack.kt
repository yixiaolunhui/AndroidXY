package com.yxlh.permission.api

/**
 *@describe 权限申请结果回调
 *@author zwl
 *@date on 2022/10/8
 */
interface IPermissionCallBack {

    /**
     * 同意授予时回调
     */
    fun onGranted()

    /**
     * 权限被拒绝授予时回调
     *
     * @param never        是否有永久拒绝
     */
    fun onDenied(never: Boolean)
}