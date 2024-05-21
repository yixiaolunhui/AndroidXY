package com.yxlh.androidxy.demo.ui.rating

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.databinding.ActivityRatingBinding
import kotlin.random.Random

class RatingActivity : AppCompatActivity() {

    private var binding: ActivityRatingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.randomizeButton?.setOnClickListener {
            randomizeRatings()
        }
    }

    private fun randomizeRatings() {
        val random = Random(System.currentTimeMillis())
        val maxRating = 5 + random.nextInt(6)
        val upRating = 1 + random.nextInt(maxRating)
        val leftRating = 1 + random.nextInt(maxRating)
        val rightRating = 1 + random.nextInt(maxRating)
        binding?.triangleRatingAnimView?.apply {
            this.maxRating = maxRating
            this.upRating = upRating
            this.leftRating = leftRating
            this.rightRating = rightRating
        }
    }
}
