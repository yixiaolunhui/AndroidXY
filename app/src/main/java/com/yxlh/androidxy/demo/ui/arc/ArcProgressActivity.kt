package com.yxlh.androidxy.demo.ui.arc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.databinding.ActivityArcProgressBinding

class ArcProgressActivity : AppCompatActivity() {

    var binding: ActivityArcProgressBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArcProgressBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initAnim()
    }

    private fun initAnim() {
        binding?.arcProgress?.setProgress(90)
    }
}