package com.yxlh.androidxy.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.R
import com.yxlh.androidxy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.expandList?.setAdapter(XYAdapter(this))
    }

}