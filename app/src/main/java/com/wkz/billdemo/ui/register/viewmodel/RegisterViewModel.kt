package com.wkz.billdemo.ui.register.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wkz.billdemo.base.BaseViewModel
import com.wkz.billdemo.data.UserData
import com.wkz.billdemo.data.UserDataBase
import com.wkz.billdemo.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterViewModel : BaseViewModel() {

    var isRegister = MutableLiveData<Boolean>()

    fun register(name: String,psw:String){
        viewModelScope.launch(Dispatchers.IO) {
            val query: UserData? = UserDataBase.userDb.userDao.queryByUserName(name)
            if (query == null) {
                val userData = UserData()
                userData.userName = name
                userData.passWord = psw
                UserDataBase.userDb.userDao.insertUser(userData)
                isRegister.postValue(true)

            } else {
                isRegister.postValue(false)
            }
        }
    }

}