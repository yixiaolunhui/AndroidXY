package com.yxlh.androidxy

import android.app.Activity
import com.yxlh.androidxy.ui.arc.ArcProgressActivity
import com.yxlh.androidxy.ui.tag.TagActivity

//单个条目
data class XYItem(var name: String, var cls: Class<out Activity>)

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
        //TODO

        return XYGroup("功能小样", items)

    }

    private fun uiItemData(): XYGroup {
        var items = ArrayList<XYItem>()
        items.add(XYItem("圆弧进度", ArcProgressActivity::class.java))
        //TODO
        return XYGroup("UI小样", items)
    }

}