package com.wkz.billdemo.base

import android.os.Bundle
import android.view.View

interface IBaseFragmentK {

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据
     * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    fun initData()

    /**
     * 此方法用于设置View显示数据
     */
    fun initView(inflateView: View?, savedInstanceState: Bundle?)

    /**
     * 此方法用于初始化view的监听
     */
    fun initListener()
}