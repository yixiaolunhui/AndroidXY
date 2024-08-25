package com.yxlh.androidxy.demo.ui.card

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxlh.androidxy.R

class CardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        val cardList = mutableListOf(
            CardData(R.drawable.shape_radius_red, "投屏"),
            CardData(R.drawable.shape_radius_blue, "酒店服务"),
            CardData(R.drawable.shape_radius_yellow, "设置")
        )
        recyclerView.adapter = CardAdapter(cardList,recyclerView)
    }
}