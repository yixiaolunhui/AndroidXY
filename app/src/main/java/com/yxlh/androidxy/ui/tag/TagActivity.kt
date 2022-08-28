package com.yxlh.androidxy.ui.tag

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.JustifyContent
import com.yxlh.androidxy.R
import com.yxlh.androidxy.databinding.ActivityTagBinding


class TagActivity : AppCompatActivity() {

    private var binding: ActivityTagBinding? = null

    private val adapter by lazy { TagAdapter() }

    private val layoutManager by lazy {
        MYFlexboxLayoutManager(this, 15).apply {
            //最多显示4行，多的折叠
            setMaxShowLine(4)
            //默认配置
            flexDirection = FlexDirection.ROW           //主轴方向为横轴
            flexWrap = FlexWrap.WRAP                    //按正常方向换行
            justifyContent = JustifyContent.FLEX_START //主轴方向
            alignItems = AlignItems.FLEX_START         //次轴方向

            foldStateListener = object : MYFlexboxLayoutManager.OnFoldStateListener {
                override fun stateChange(state: MYFlexboxLayoutManager.State) {
                    if (state == MYFlexboxLayoutManager.State.OPEN) {
                        binding?.open?.setImageResource(R.drawable.tag_icon_up)
                    } else {
                        binding?.open?.setImageResource(R.drawable.tag_icon_down)
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTagBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initList()

        //添加数据
        binding?.add?.setOnClickListener {
            adapter.addTag(TagDataHelper.tagList().random())
        }

        //展开/折叠
        binding?.open?.setOnClickListener {
            layoutManager.toggle()
        }

        //居左
        binding?.left?.setOnClickListener {
            layoutManager.justifyContent = JustifyContent.FLEX_START
        }

        //居中
        binding?.center?.setOnClickListener {
            layoutManager.justifyContent = JustifyContent.CENTER
        }

        //居右
        binding?.right?.setOnClickListener {
            layoutManager.justifyContent = JustifyContent.FLEX_END
        }

        //列表
        binding?.list?.setOnClickListener {
            startActivity(Intent(this, TagListActivity::class.java))
        }

    }

    private fun initList() {
        adapter.setTags(TagDataHelper.tagList())
        binding?.tagList?.layoutManager = layoutManager
        binding?.tagList?.adapter = adapter
    }
}