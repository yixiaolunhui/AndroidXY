package com.yxlh.androidxy.ui.tag

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexLine
import com.google.android.flexbox.FlexboxLayoutManager


/**
 *@author zwl
 *@date on 2022/8/27
 */
class MYFlexboxLayoutManager @JvmOverloads constructor(context: Context, var space: Int = 0) : FlexboxLayoutManager(context) {

    private var NOT_SET = Integer.MAX_VALUE

    private var mLineCount = NOT_SET

    private var mLinesCountTemp = NOT_SET

    private var mState = State.INIT

    var foldStateListener: OnFoldStateListener? = null

    enum class State {
        INIT, OPEN, FOLD,
    }


    /**
     * 设置显示最大行数
     */
    fun setMaxShowLine(lines: Int) {
        this.mLinesCountTemp = lines
        this.mLineCount = lines
    }


    /**
     * 切换展开/折叠
     */
    fun toggle() {
        if (mState == State.OPEN) {
            close()
        } else {
            open()
        }
    }

    /**
     * 展开
     */
    private fun open() {
        this.mLineCount = NOT_SET
        requestLayout()
    }

    /**
     * 折叠
     */
    private fun close() {
        this.mLineCount = mLinesCountTemp
        requestLayout()
    }


    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        val generateDefaultLayoutParams = super.generateDefaultLayoutParams()
        generateDefaultLayoutParams.setMargins(space, space, space, space)
        return generateDefaultLayoutParams
    }

    override fun getFlexLinesInternal(): MutableList<FlexLine> {
        val originList = super.getFlexLinesInternal()
        //数据 总行数
        val totalLines = originList.size
        //总行数>设定行数，进行折叠
        if (totalLines > mLineCount) {
            //对数据处理（清理从设定行到最大行的数据）
            originList.subList(mLineCount, totalLines).clear()
            //当前状态不是折叠状态时，再处理状态改变回调及状态更新，防止返回多次问题
            if (mState != State.FOLD) {
                foldStateListener?.stateChange(State.FOLD)
                mState = State.FOLD
            }
        }
        //没有设置行数时，默认打开状态
        if (mLineCount == NOT_SET) {
            //当前状态不是打开状态，进行打开操作，并回调状态改变
            if (mState != State.OPEN) {
                foldStateListener?.stateChange(State.OPEN)
                mState = State.OPEN
            }
        }
        return originList
    }


    interface OnFoldStateListener {
        fun stateChange(state: State)
    }

}