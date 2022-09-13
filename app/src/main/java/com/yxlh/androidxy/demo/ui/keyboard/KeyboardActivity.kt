package com.yxlh.androidxy.demo.ui.keyboard

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.databinding.ActivityKeyboardBinding

class KeyboardActivity : AppCompatActivity() {
    var binding: ActivityKeyboardBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeyboardBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        KeyboardHelper.setKeyBoardListener(object :KeyboardListener{
            override fun onKeyBoardStart() {
                Log.e("111111","onKeyBoardStart")
            }

            override fun onKeyBoardChange(height: Int) {
                Log.e("111111","onKeyBoardChange  height=$height")
            }

            override fun onKeyBoardEnd() {
                Log.e("111111","onKeyBoardEnd")
            }

        })

        binding?.change?.setOnClickListener {
            KeyboardHelper.showOrHideKeyBoard(binding?.editText!!)
        }

    }
}