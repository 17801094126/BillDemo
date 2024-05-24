package com.wkz.billdemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding,VM : ViewModel>(@LayoutRes private val layoutId: Int) : AppCompatActivity(),
    IBaseFragmentK {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected lateinit var mViewModel:VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding  = createViewBinding(layoutInflater)
        setContentView(binding.root)
        mViewModel = createViewModel()
        initView(binding.root, savedInstanceState)
        initData()
        initListener()
    }

    abstract fun createViewBinding(view: LayoutInflater): VB?
    abstract fun createViewModel(): VM

    override fun initListener() {}

    override fun initData() {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return DataBindingUtil.inflate(inflater,layoutId,container,false) as VB
    }
}