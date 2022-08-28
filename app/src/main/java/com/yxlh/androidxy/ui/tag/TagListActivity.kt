package com.yxlh.androidxy.ui.tag

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxlh.androidxy.databinding.ActivityTagListBinding

class TagListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTagListBinding.inflate(layoutInflater) }
    private val adapter by lazy { TagListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.taglist.layoutManager=LinearLayoutManager(this)
        binding.taglist.adapter=adapter
        adapter.setTagList(TagDataHelper.tagBeanList())
    }
}