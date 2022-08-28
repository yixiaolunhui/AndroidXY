package com.yxlh.androidxy.ui.arc

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.databinding.ActivityArcProgressBinding

class ArcProgressActivity : AppCompatActivity() {

    var binding: ActivityArcProgressBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArcProgressBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val animator = ObjectAnimator.ofInt(0, 90)
        animator.duration = 2000
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            binding?.arcProgress?.setProgress(progress)
        }
        animator.start()
    }
}