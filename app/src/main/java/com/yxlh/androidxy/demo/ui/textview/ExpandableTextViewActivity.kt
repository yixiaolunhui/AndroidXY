package com.yxlh.androidxy.demo.ui.textview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.databinding.ActivityExpandableTextviewBinding

class ExpandableTextViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityExpandableTextviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var text="Your very long  very long text here.our very long very long text here.our very long very long text here.our very long very long text here.our very long very long text here.our very long very long text here.our very longtext here.our very long text here.Your very long text hereYour very long text hereYour very long text hereYour very long text here"
        binding.expandableTextView.setText(text)
    }
}