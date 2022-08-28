package com.yxlh.androidxy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.expandList?.setAdapter(XYAdapter(this))
    }
}




