package com.yxlh.androidxy.demo.ui.tag

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.yxlh.androidxy.R


/**
 *@author zwl
 *@date on 2022/8/27
 */
class TagAdapter : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    private var tagList: MutableList<String>? = null

    fun setTags(tags: MutableList<String>) {
        this.tagList = tags
        notifyDataSetChanged()
    }

    fun addTag(tag: String) {
        if (tagList == null) {
            tagList = ArrayList()
        }
        tagList?.add(tag)
        notifyDataSetChanged()
    }


    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tagName: AppCompatTextView? = null
        init {
            tagName = itemView.findViewById(R.id.tag_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = View.inflate(parent.context, R.layout.item_tag_view, null)
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.tagName?.text = tagList?.get(position)
    }

    override fun getItemCount() = tagList?.size ?: 0
}