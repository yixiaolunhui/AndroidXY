package com.yxlh.androidxy.demo.ui.scroller

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxlh.androidxy.R
import com.yxlh.androidxy.databinding.ActivityScrollerBinding
import kotlin.random.Random

/**
 *@author zwl
 *@date on 2024/6/15
 */
class ScrollerActivity : AppCompatActivity() {

    private var binding: ActivityScrollerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //内容布局
        binding?.content?.layoutManager = LinearLayoutManager(this)
        binding?.content?.adapter = ColorAdapter(false)

        //底部布局
        binding?.bottomContent?.layoutManager = LinearLayoutManager(this)
        binding?.bottomContent?.adapter = ColorAdapter(true)

        binding?.content?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding?.scrollerLayout?.showNavigation()
                }
            }
        })

    }
}






class ColorAdapter(private var isColor: Boolean) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private val colors = List(100) { getRandomColor() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ColorViewHolder(view,isColor)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colors[position], position)
    }

    override fun getItemCount(): Int = colors.size

    private fun getRandomColor(): Int {
        val random = Random.Default
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

    class ColorViewHolder(itemView: View, private var isColor: Boolean) : RecyclerView.ViewHolder(itemView) {
        fun bind(color: Int, position: Int) {
            if(isColor){
                itemView.setBackgroundColor(color)
            }
            itemView.findViewById<TextView>(R.id.color_tv).text = "$position"
        }
    }
}
