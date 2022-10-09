package com.yxlh.androidxy.main

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.yxlh.androidxy.databinding.ActivityMainBinding
import com.yxlh.permission.XYPermission
import com.yxlh.permission.api.IPermissionCallBack
import com.yxlh.permission.intercept.Interceptor
import com.yxlh.permission.intercept.InterceptorApi

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.expandList?.setAdapter(XYAdapter(this))

        XYPermission.with(this)
            .permissions(arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA))
            .intercept(object : Interceptor {
                override fun intercept(chain: InterceptorApi) {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("提示")
                        .setMessage("授权是为了让您更好的使用此APP")
                        .setPositiveButton("确定") { dialog, which ->
                            dialog.dismiss()
                            chain.proceed()
                        }
                        .setNegativeButton("取消") { dialog, which ->
                            dialog.dismiss()
                            chain.interrupt()
                        }
                        .show()
                }
            })
            .callBack(object : IPermissionCallBack {
                override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                    Log.e("1111111", "onGranted")
                }

                override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                    Log.e("1111111", "onDenied")
                }

            })
            .request()
    }
}