package com.yxlh.androidxy.ui.tag

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.yxlh.androidxy.R


/**
 *@author zwl
 *@date on 2022/8/27
 */
class TagListAdapter : RecyclerView.Adapter<TagListAdapter.TagViewHolder>() {

    private var tagList: MutableList<TagBean>? = null


    fun setTagList(tags: MutableList<TagBean>) {
        this.tagList = tags
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = View.inflate(parent.context, R.layout.item_tag_list, null)
        return TagViewHolder(view)
    }


    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.title?.text = tagList?.get(position)?.title

        val layoutManager = MYFlexboxLayoutManager(holder.tagList!!.context, 15).apply {
            setMaxShowLine(4)
            foldStateListener = object : MYFlexboxLayoutManager.OnFoldStateListener {
                override fun stateChange(state: MYFlexboxLayoutManager.State) {
                    if (state == MYFlexboxLayoutManager.State.OPEN) {
                        holder?.open?.setImageResource(R.drawable.tag_icon_up)
                    } else {
                        holder?.open?.setImageResource(R.drawable.tag_icon_down)
                    }
                }
            }
        }
        val adapter = TagAdapter()
        holder.tagList.layoutManager = layoutManager
        holder?.tagList?.adapter = adapter

        tagList?.get(position)?.tags?.let { adapter.setTags(it) }

        holder?.open?.setOnClickListener { layoutManager.toggle() }

    }

    override fun getItemCount() = tagList?.size ?: 0


    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: AppCompatTextView
        var open: AppCompatImageView
        var tagList: RecyclerView

        init {
            title = itemView.findViewById(R.id.tag_title)
            open = itemView.findViewById(R.id.open)
            tagList = itemView.findViewById(R.id.tag_list)
        }
    }
}