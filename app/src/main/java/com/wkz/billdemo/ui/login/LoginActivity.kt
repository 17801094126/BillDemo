package com.wkz.billdemo.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import com.wkz.billdemo.Config
import com.wkz.billdemo.R
import com.wkz.billdemo.base.BaseActivity
import com.wkz.billdemo.databinding.ActivityLoginBinding
import com.wkz.billdemo.ui.bill.BillActivity
import com.wkz.billdemo.ui.login.viewmodel.LoginViewModel
import com.wkz.billdemo.ui.register.RegisterActivity
import com.wkz.billdemo.utils.PreferenceUtil
import com.wkz.billdemo.utils.ToastUtils

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    override fun createViewModel(): LoginViewModel = LoginViewModel()

    override fun createViewBinding(view: LayoutInflater): ActivityLoginBinding = ActivityLoginBinding.inflate(view)

    override fun initView(inflateView: View?, savedInstanceState: Bundle?) {

        PreferenceUtil.init(this)

        if (PreferenceUtil.getBoolean(Config.IS_STORAGE, false)) {
            binding.checkbox.isChecked = true
            val userName = PreferenceUtil.getString(Config.USER_NAME, "")
            val psw = PreferenceUtil.getString(Config.PASS_WORD, "")
            if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(psw)) {
                binding.username.setText(userName)
                binding.password.setText(psw)
            }
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.queryData.observe(this){
            if (it!=null){
                if (mViewModel.passWordData.value == it.passWord) {
                    PreferenceUtil.commitString(Config.USER_NAME, mViewModel.userNameData.value)
                    PreferenceUtil.commitString(Config.PASS_WORD, mViewModel.passWordData.value)
                    ToastUtils.showCustomCenterToast(this, "登陆成功")
                    startActivity(Intent(this, BillActivity::class.java))
                    finish()
                } else {
                    ToastUtils.showCustomCenterToast(this, "密码输入错误，请重新输入")
                }
            }else{
                ToastUtils.showCustomCenterToast(this, "登陆失败，请先注册账号")
            }
        }
    }

    override fun initListener() {
        super.initListener()
        binding.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                PreferenceUtil.commitBoolean(Config.IS_STORAGE, true)
            } else {
                PreferenceUtil.commitBoolean(Config.IS_STORAGE, false)
            }
        })
    }


    fun check(): Boolean {
        val n: String = binding.username.text.toString()
        val p: String = binding.password.text.toString()
        if (TextUtils.isEmpty(n)) {
            ToastUtils.showCustomCenterToast(this, "请填写用户名")
            return false
        }
        if (TextUtils.isEmpty(p)) {
            ToastUtils.showCustomCenterToast(this, "请填写密码")
            return false
        }
        return true
    }

    fun login(v: View?) {
        if (!check()) {
            return
        }
        val n: String = binding.username.text.toString()
        val p: String = binding.password.text.toString()
        mViewModel.userNameData.value = n
        mViewModel.passWordData.value = p
        mViewModel.queryByName(n)

    }

    fun toRegister(v: View?) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}