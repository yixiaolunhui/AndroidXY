package com.yxlh.androidxy

import android.Manifest
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.yxlh.androidxy.demo.function.jump.ActivityStartActivity
import com.yxlh.androidxy.demo.ui.arc.ArcProgressActivity
import com.yxlh.androidxy.demo.ui.avatar.AvatarOverlayActivity
import com.yxlh.androidxy.demo.ui.carousel.CarouselActivity
import com.yxlh.androidxy.demo.ui.codeet.CodeActivity
import com.yxlh.androidxy.demo.ui.cycle.screens.CycleActivity
import com.yxlh.androidxy.demo.ui.fragments.FragmentListActivity
import com.yxlh.androidxy.demo.ui.rating.RatingActivity
import com.yxlh.androidxy.demo.ui.scroller.ScrollerActivity
import com.yxlh.androidxy.demo.ui.tag.TagActivity
import com.yxlh.androidxy.demo.ui.textview.ExpandableTextViewActivity
import com.yxlh.permission.XYPermission
import com.yxlh.permission.api.IPermissionCallBack
import com.yxlh.permission.intercept.Interceptor
import com.yxlh.permission.intercept.InterceptorApi

interface ItemClickListener {
    fun onClick(context: Context)
}

//单个条目
data class XYItem(var name: String, var cls: Class<out Activity>? = null, var click: ItemClickListener? = null)

//组别
data class XYGroup(var name: String, var items: List<XYItem>)

/**
 *@author zwl
 *@date on 2022/8/23
 */
object XYDataConfig {


    fun data(): List<XYGroup> {
        var groups = ArrayList<XYGroup>()
        groups.add(uiItemData())
        groups.add(funItemData())
        return groups
    }

    private fun funItemData(): XYGroup {
        var items = ArrayList<XYItem>()
        items.add(XYItem("标签", TagActivity::class.java))
        items.add(XYItem("头像叠加", AvatarOverlayActivity::class.java))
        items.add(XYItem("圆弧进度", ArcProgressActivity::class.java))
        items.add(XYItem("三角形评分控件", RatingActivity::class.java))
        items.add(XYItem("展开收起TextView", ExpandableTextViewActivity::class.java))
        items.add(XYItem("滑动布局", ScrollerActivity::class.java))
        items.add(XYItem("Fragments", FragmentListActivity::class.java))
        //TODO

        return XYGroup("自定义", items)

    }

    private fun uiItemData(): XYGroup {
        var items = ArrayList<XYItem>()
        items.add(XYItem("业务跳转", ActivityStartActivity::class.java))
        items.add(XYItem("验证码框", CodeActivity::class.java))
        items.add(XYItem("权限申请", click = object : ItemClickListener {
            override fun onClick(context: Context) {
                XYPermission.with(context).permissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA
                    )
                ).intercept(object : Interceptor {
                    override fun intercept(chain: InterceptorApi) {
                        var location = arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                        if (!XYPermission.with(context).isGrant(location)) {
                            AlertDialog.Builder(context).setTitle("提示").setMessage("请授权位置权限，为了更好的为你服务").setCancelable(false)
                                .setPositiveButton("确定") { dialog, which ->
                                    dialog.dismiss()
                                    chain.proceed()
                                }.setNegativeButton("取消") { dialog, which ->
                                    dialog.dismiss()
                                    chain.interrupt()
                                }.show()
                        } else {
                            chain.proceed()
                        }

                    }
                }).intercept(object : Interceptor {
                    override fun intercept(chain: InterceptorApi) {
                        var location = arrayOf(
                            Manifest.permission.CAMERA,
                        )
                        if (!XYPermission.with(context).isGrant(location)) {
                            AlertDialog.Builder(context).setTitle("提示2").setMessage("请授权相机权限，为了修改您的头像").setCancelable(false)
                                .setPositiveButton("确定") { dialog, which ->
                                    dialog.dismiss()
                                    chain.proceed()
                                }.setNegativeButton("取消") { dialog, which ->
                                    dialog.dismiss()
                                    chain.interrupt()
                                }.show()
                        } else {
                            chain.proceed()
                        }

                    }

                }).callBack(object : IPermissionCallBack {
                    override fun onGranted() {
                        Log.e("XYPermission", "onGranted")
                    }

                    override fun onDenied(never: Boolean) {
                        Log.e("XYPermission", "onDenied  never=$never")
                    }

                }).request()
            }
        }))
        //TODO
        return XYGroup("功能", items)
    }

}