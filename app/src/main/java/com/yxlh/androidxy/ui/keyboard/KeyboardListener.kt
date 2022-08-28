package com.yxlh.androidxy.ui.keyboard

/**
 *@describe 键盘监听
 *@author zwl
 *@date on 2022/8/26
 */
interface KeyboardListener {

    fun onKeyBoardStart()

    fun onKeyBoardChange(height: Int)

    fun onKeyBoardEnd()
}