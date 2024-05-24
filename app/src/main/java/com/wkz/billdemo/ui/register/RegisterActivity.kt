package com.wkz.billdemo.ui.register

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.wkz.billdemo.base.BaseActivity
import com.wkz.billdemo.R
import com.wkz.billdemo.databinding.ActivityRegisterBinding
import com.wkz.billdemo.ui.register.viewmodel.RegisterViewModel
import com.wkz.billdemo.utils.ToastUtils

class RegisterActivity :BaseActivity<ActivityRegisterBinding, RegisterViewModel>(R.layout.activity_register) {
    override fun createViewBinding(view: LayoutInflater): ActivityRegisterBinding = ActivityRegisterBinding.inflate(view)

    override fun createViewModel(): RegisterViewModel = RegisterViewModel()

    override fun initView(inflateView: View?, savedInstanceState: Bundle?) {

    }

    override fun initData() {
        super.initData()
        mViewModel.isRegister.observe(this){
            if (it){
                ToastUtils.showCustomCenterToast(this, "注册成功,请登录")
                finish()
            }else{
                ToastUtils.showCustomCenterToast(this, "注册失败,用户已存在")
            }
        }
    }

    fun check(): Boolean {
        val n: String = binding.username.text.toString()
        val p: String = binding.password.text.toString()
        val cp: String = binding.conPassword.text.toString()
        if (TextUtils.isEmpty(n)) {
            ToastUtils.showCustomCenterToast(this, "请填写用户名")
            return false
        }
        if (TextUtils.isEmpty(p)) {
            ToastUtils.showCustomCenterToast(this, "请填写密码")
            return false
        }
        if (cp != p) {
            ToastUtils.showCustomCenterToast(this, "两次密码输入不一致，请重新输入")
            return false
        }
        return true
    }

    fun register(v: View?) {
        if (!check()) {
            return
        }
        val n: String = binding.username.text.toString()
        val p: String = binding.password.text.toString()
        mViewModel.register(n,p)
    }
}