package com.yxlh.androidxy.demo.ui.avatar

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.yxlh.androidxy.R

/**
 *@describe 头像适配器
 *@author zwl
 *@date on 2022/9/2
 */
class AvatarAdapter : RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>() {

    private var avatarList: MutableList<String>? = null

    fun setAvatarList(avatars: MutableList<String>) {
        this.avatarList = avatars
        notifyDataSetChanged()
    }

    fun addAvatar(avatar: String) {
        if (avatarList == null) {
            avatarList = ArrayList()
        }
        avatarList?.add(avatar)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        var view = View.inflate(parent.context, R.layout.avatar_item_view, null)
        return AvatarViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        holder.avatarIcon?.let {
            Glide.with(it.context).load(avatarList?.get(position) ?: "").into(it)
        }
    }

    override fun getItemCount() = avatarList?.size ?: 0


    class AvatarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatarIcon: ShapeableImageView? = null

        init {
            avatarIcon = itemView.findViewById(R.id.avatar)
        }
    }
}

