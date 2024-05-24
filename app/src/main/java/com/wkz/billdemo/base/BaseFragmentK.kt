package com.wkz.billdemo.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragmentK<VB : ViewBinding,VM : ViewModel>(@LayoutRes private val layoutId: Int) : Fragment(),
    IBaseFragmentK {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected var mViewModel:VM? = null

    private var isPrepared = false //是否准备好页面

    private var isVisible = false //页面是否可见

    protected var isLoaded = false //是否加载完数据

    lateinit var mContext: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding  = createViewBinding(ViewBindingInflater.bind(inflater,layoutId,container))
        initView(binding.root, savedInstanceState)

        //判断是否为懒加载，懒加载则不执行以下代码
        if (!isLazyLoad()) {
            initData()
            initListener()
            isLoaded = true
        }
        return binding.root
    }

    abstract fun createViewBinding(view: View): VB?
    abstract fun createViewModel(): VM?

    override fun onStart() {
        super.onStart()
        if (isLazyLoad()) {
            isPrepared = true
            loadData()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //判断是否为懒加载方式
        if (isLazyLoad()) {
            //当前页面可见，可见状态开关设置为true，并且加载数据
            if (isVisibleToUser) {
                isVisible = true
                loadData()
            } else {
                isVisible = false
            }
        }
    }


    override fun initListener() {}

    override fun initData() {}

    /**
     * 判断并且加载数据
     */
    protected fun loadData() {
        //如果是懒加载，则将可视状态设置为true
        if (!isLazyLoad()) {
            isVisible = true
        }
        //判断当前是否需要加载数据
        if (!isPrepared || !isVisible || isLoaded) {
            return
        }
        initData()
        initListener()
        //加载完成状态改变
        isLoaded = true
    }

    /**
     * 是否注册eventbus事件
     *
     * @return
     */
    open fun isRegisterEventBus(): Boolean {
        return false
    }


    /**
     * 是否为懒加载的开关，默认为不是懒加载，子类重写改变当前fragment的加载方式
     *
     * @return true :lazy，false：noLazy
     */
    fun isLazyLoad(): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return DataBindingUtil.inflate(inflater,layoutId,container,false) as VB
    }
}

object ViewBindingInflater {
    @Suppress("UNCHECKED_CAST")
    fun  bind(inflater: LayoutInflater, @LayoutRes layoutId: Int, container: ViewGroup?): View {
        return inflater.inflate(layoutId,container,false)
    }
}
