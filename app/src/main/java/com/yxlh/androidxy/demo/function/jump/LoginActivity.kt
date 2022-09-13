package com.yxlh.androidxy.demo.function.jump

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yxlh.androidxy.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginSuccess(view: View) {
        UserDataHelper.isLogin=true
        setResult(Activity.RESULT_OK)
        finish()
    }
}