package com.yxlh.androidxy.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.appcompat.widget.AppCompatTextView
import com.yxlh.androidxy.R
import com.yxlh.androidxy.XYDataConfig
import com.yxlh.androidxy.XYGroup
import com.yxlh.androidxy.XYItem


/**
 *@describe
 *@author zwl
 *@date on 2022/8/23
 */
class XYAdapter(var context: Context) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return XYDataConfig.data().size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return XYDataConfig.data()[groupPosition]?.items?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): XYGroup {
        return XYDataConfig.data()[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): XYItem {
        return XYDataConfig.data()[groupPosition].items[childPosition]
    }

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int) = childPosition.toLong()

    override fun hasStableIds() = true

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        var holder: GroupViewHolder?
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_group, null)
            holder = GroupViewHolder(view)
            view.tag = holder
        } else {
            holder = convertView?.tag as GroupViewHolder?
        }
        holder?.groupName?.text = getGroup(groupPosition).name
        return view!!
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        var holder: ChildViewHolder?
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_child, null)
            holder = ChildViewHolder(view)
            view.tag = holder
        } else {
            holder = convertView?.tag as ChildViewHolder?
        }
        view?.setOnClickListener {
            var intent=Intent(context, getChild(groupPosition, childPosition).cls)
            context.startActivity(intent)
        }
        holder?.childName?.text = getChild(groupPosition, childPosition).name
        return view!!
    }

    internal class GroupViewHolder(view: View?) {
        var groupName: AppCompatTextView? = null

        init {
            groupName = view?.findViewById<AppCompatTextView>(R.id.group_name)
        }
    }

    internal class ChildViewHolder(view: View?) {
        var childName: AppCompatTextView? = null

        init {
            childName = view?.findViewById<AppCompatTextView>(R.id.child_name)
        }
    }

}