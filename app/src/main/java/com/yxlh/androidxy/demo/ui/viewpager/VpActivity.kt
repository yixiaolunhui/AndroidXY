package com.yxlh.androidxy.demo.ui.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.R
import com.yxlh.androidxy.demo.ui.viewpager.vp.AutoHeightViewPager

class VpActivity : AppCompatActivity() {
    private var mAutoHeightVp: AutoHeightViewPager? = null

    private var mAdapter: AutoHeightPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vp)

        val viewList: MutableList<View> = ArrayList()
        viewList.add(LayoutInflater.from(this).inflate(R.layout.view_demo_1, null))
        viewList.add(LayoutInflater.from(this).inflate(R.layout.view_demo_2, null))

        mAutoHeightVp =findViewById(R.id.viewpager)
        mAutoHeightVp?.setAdapter(AutoHeightPagerAdapter().also { mAdapter = it })
        mAdapter?.setViews(viewList)
        mAutoHeightVp?.setCurrentItem(1)
    }
}