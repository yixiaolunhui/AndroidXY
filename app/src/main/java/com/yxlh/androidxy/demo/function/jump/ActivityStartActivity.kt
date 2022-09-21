package com.yxlh.androidxy.demo.function.jump

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.R
import com.yxlh.androidxy.demo.function.chain.Chain
import com.yxlh.androidxy.demo.function.chain.ChainInterceptor
import com.yxlh.androidxy.demo.function.launch_kotlin.startForResult

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
            //方式1
//            LaunchHelper.builder()
//                .with(activity)
//                .to(LoginActivity::class.java)
//                .callback { _, resultCode, _ ->
//                    if (resultCode == Activity.RESULT_OK && UserDataHelper.isLogin) {
//                        chain.proceed()
//                    }
//                }
//                .start()

            //方式2
            activity.startForResult(
                requestCode = 100,
                Intent(activity, LoginActivity::class.java),
            ) { _, resultCode, _ ->
                if (resultCode == Activity.RESULT_OK && UserDataHelper.isLogin) {
                    chain.proceed()
                }
            }

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
            //方式1
//            LaunchHelper.builder()
//                .with(activity)
//                .to(AuthActivity::class.java)
//                .callback { _, resultCode, _ ->
//                    if (resultCode == Activity.RESULT_OK && UserDataHelper.isAuth) {
//                        chain.proceed()
//                    }
//                }
//                .start()

            //方式2
            activity.startForResult(
                requestCode = 100,
                Intent(activity, AuthActivity::class.java),
            ) { _, resultCode, _ ->
                if (resultCode == Activity.RESULT_OK && UserDataHelper.isLogin) {
                    chain.proceed()
                }
            }
        } else {
            chain.proceed()
        }
    }
}