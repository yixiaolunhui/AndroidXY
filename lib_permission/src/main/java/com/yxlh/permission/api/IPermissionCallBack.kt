package com.yxlh.permission.api

/**
 *@describe 权限申请结果回调
 *@author zwl
 *@date on 2022/10/8
 */
interface IPermissionCallBack {

    /**
     * 有权限被同意授予时回调
     *
     * @param permissions           请求成功的权限组
     * @param all                   是否全部授予了
     */
    fun onGranted(permissions: MutableList<String>?, all: Boolean)

    /**
     * 有权限被拒绝授予时回调
     *
     * @param permissions            请求失败的权限组
     * @param never                  是否有某个权限被永久拒绝了
     */
    fun onDenied(permissions: MutableList<String>?, never: Boolean)
}