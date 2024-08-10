package com.yxlh.androidxy.demo.ui.viewpager

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.yxlh.androidxy.demo.ui.viewpager.vp.AutoHeightPager

class AutoHeightPagerAdapter : PagerAdapter(), AutoHeightPager {

    private val viewList = mutableListOf<View>()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = viewList[position]
        val parent = view.parent as? ViewGroup
        parent?.removeView(view)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun setViews(views: List<View>) {
        viewList.clear()
        viewList.addAll(views)
        notifyDataSetChanged()
    }

    override fun getView(position: Int): View? {
        return viewList.getOrNull(position)
    }

    override fun getCount(): Int {
        return viewList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

}