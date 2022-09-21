package com.yxlh.androidxy.demo.function.jump

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yxlh.androidxy.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    fun authSuccess(view: View) {
        UserDataHelper.isAuth=true
        var intent= Intent()
        intent.putExtra("name","dalong")
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}