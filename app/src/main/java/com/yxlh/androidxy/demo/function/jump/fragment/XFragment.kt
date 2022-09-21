package com.yxlh.androidxy.demo.function.jump.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.yxlh.androidxy.R
import com.yxlh.androidxy.demo.function.jump.AuthActivity
import com.yxlh.androidxy.demo.function.launch_kotlin.startForResult

/**
 *@author zwl
 *@date on 2022/9/21
 */
class XFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_x, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AppCompatButton>(R.id.jumpAuth).setOnClickListener {
            startForResult(
                requestCode = 100,
                Intent(requireContext(), AuthActivity::class.java)
            ) { requestCode, resultCode, data ->
                Log.e("1111111", "XFragment requestCode=$requestCode resultCode=$resultCode name=${data?.getStringExtra("name")}")
            }
        }
    }
}