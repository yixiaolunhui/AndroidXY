package com.yxlh.androidxy.demo.function.launch_kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

/**
 * 默认requestCode
 */
const val REQUEST_CODE = 1000

/**
 * android Fragment startForResult
 */
fun android.app.Fragment.startForResult(
    requestCode: Int = REQUEST_CODE,
    intent: Intent,
    callBack: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit,
) {
    val fragment = FragmentForResult()
    fragment.intent = intent
    fragment.requestCode = requestCode
    fragment.onActivityResult = { requestCode, resultCode, data ->
        callBack?.invoke(requestCode, resultCode, data)
        if (fragment.isAdded) {
            childFragmentManager.beginTransaction().remove(fragment)
        }
    }
    childFragmentManager.beginTransaction().apply {
        if (fragment.isAdded) {
            remove(fragment)
        }
        add(fragment, fragment.toString())
    }.commitAllowingStateLoss()
}


/**
 * androidx Fragment startForResult
 *
 */
fun androidx.fragment.app.Fragment.startForResult(
    requestCode: Int = REQUEST_CODE,
    intent: Intent,
    callBack: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit,
) {
    val fragment = XFragmentForResult()
    fragment.intent = intent
    fragment.requestCode = requestCode
    fragment.onActivityResult = { requestCode, resultCode, data ->
        callBack?.invoke(requestCode, resultCode, data)
        if (fragment.isAdded) {
            childFragmentManager.beginTransaction().remove(fragment)
        }
    }
    childFragmentManager.beginTransaction().apply {
        if (fragment.isAdded) {
            remove(fragment)
        }
        add(fragment, fragment.toString())
    }.commitNowAllowingStateLoss()
}


/**
 * Activity startForResult
 */
fun Activity.startForResult(
    requestCode: Int = REQUEST_CODE,
    intent: Intent,
    callBack: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit,
) {
    //FragmentActivity
    if (this is FragmentActivity) {
        val fragment = XFragmentForResult()
        fragment.intent = intent
        fragment.requestCode = requestCode
        fragment.onActivityResult = { requestCode, resultCode, data ->
            callBack?.invoke(requestCode, resultCode, data)
            if (fragment.isAdded) {
                supportFragmentManager.beginTransaction().remove(fragment)
            }
        }
        supportFragmentManager.beginTransaction().apply {
            if (fragment.isAdded) {
                remove(fragment)
            }
            add(fragment, fragment.toString())
        }.commitNowAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }
    //Activity
    else {
        val fragment = FragmentForResult()
        fragment.intent = intent
        fragment.requestCode = requestCode
        fragment.onActivityResult = { requestCode, resultCode, data ->
            callBack?.invoke(requestCode, resultCode, data)
            if (fragment.isAdded) {
                fragmentManager.beginTransaction().remove(fragment)
            }
        }
        fragmentManager.beginTransaction().apply {
            if (fragment.isAdded) {
                remove(fragment)
            }
            add(fragment, fragment.toString())
        }.commitAllowingStateLoss()
        fragmentManager.executePendingTransactions()
    }

}


class XFragmentForResult : androidx.fragment.app.Fragment() {
    lateinit var onActivityResult: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit
    var intent: Intent? = null
    var requestCode: Int = REQUEST_CODE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let { startActivityForResult(it, requestCode) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResult?.invoke(requestCode, resultCode, data)
    }

}


class FragmentForResult : android.app.Fragment() {
    lateinit var onActivityResult: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit
    var intent: Intent? = null
    var requestCode: Int = REQUEST_CODE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let { startActivityForResult(it, requestCode) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResult?.invoke(requestCode, resultCode, data)
    }

}




