package com.yxlh.androidxy.demo.function.jump

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.R
import com.yxlh.androidxy.demo.function.chain.Chain
import com.yxlh.androidxy.demo.function.chain.ChainInterceptor
import com.yxlh.androidxy.demo.function.launch.LaunchHelper

class ActivityStartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }


    fun clear(view: View) {
        UserDataHelper.clear()
    }


    fun jumpDetail(view: View) {
        ActivityStart.with(this)
            .addIntercept(LoginIntercept(this))
            .addIntercept(AuthIntercept(this))
            .go(DetailActivity::class.java)
    }
}


/**
 * 登录拦截
 */
class LoginIntercept(var activity: Activity) : ChainInterceptor {
    override fun intercept(chain: Chain) {
        if (!UserDataHelper.isLogin) {
            LaunchHelper.builder()
                .with(activity)
                .to(LoginActivity::class.java)
                .callback { _, resultCode, _ ->
                    if (resultCode == Activity.RESULT_OK && UserDataHelper.isLogin) {
                        chain.proceed()
                    }
                }
                .start()
        } else {
            chain.proceed()
        }

    }
}

/**
 * 认证拦截
 */
class AuthIntercept(var activity: Activity) : ChainInterceptor {
    override fun intercept(chain: Chain) {
        if (!UserDataHelper.isAuth) {
            LaunchHelper.builder()
                .with(activity)
                .to(AuthActivity::class.java)
                .callback { _, resultCode, _ ->
                    if (resultCode == Activity.RESULT_OK && UserDataHelper.isAuth) {
                        chain.proceed()
                    }
                }
                .start()
        } else {
            chain.proceed()
        }
    }
}