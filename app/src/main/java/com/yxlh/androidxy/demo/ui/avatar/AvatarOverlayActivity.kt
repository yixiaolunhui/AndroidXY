package com.yxlh.androidxy.demo.ui.avatar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.databinding.ActivityAvatarOverlayBinding


/**
 * 头像叠加
 */
class AvatarOverlayActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAvatarOverlayBinding.inflate(layoutInflater) }

    private val adapter by lazy { AvatarAdapter()}

    private val avatarList = mutableListOf(
        "https://img1.baidu.com/it/u=2662874570,3746847075&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=313",
        "https://img2.baidu.com/it/u=2327765005,2697502655&fm=253&fmt=auto&app=138&f=JPEG?w=600&h=354",
        "https://img0.baidu.com/it/u=128710991,265282450&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281",
        "https://img1.baidu.com/it/u=1870757533,82140529&fm=253&fmt=auto&app=138&f=JPEG?w=658&h=468",
        "https://img2.baidu.com/it/u=2670411182,2972126738&fm=253&fmt=auto&app=138&f=JPEG?w=723&h=500",
//        "https://img1.baidu.com/it/u=3289683275,3425115592&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500",
//        "https://img0.baidu.com/it/u=1536429656,2044029216&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500",
//        "https://img2.baidu.com/it/u=1042057112,565498267&fm=253&fmt=auto&app=138&f=JPEG?w=633&h=500",
//        "https://img0.baidu.com/it/u=143364511,154568207&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400",
//        "https://img2.baidu.com/it/u=3513021736,1144094673&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=355",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.avatarList.adapter = adapter
        adapter.setAvatarList(avatarList)
        AvatarOverlayHelper.init(this, binding.avatarList, true)



        binding.add.setOnClickListener {
            avatarList.randomOrNull()?.let { it1 -> adapter.addAvatar(it1) }
        }
    }


}