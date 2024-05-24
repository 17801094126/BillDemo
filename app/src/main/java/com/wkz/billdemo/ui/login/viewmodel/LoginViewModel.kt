package com.wkz.billdemo.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wkz.billdemo.base.BaseViewModel
import com.wkz.billdemo.data.UserData
import com.wkz.billdemo.data.UserDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginViewModel : BaseViewModel() {

    var queryData = MutableLiveData<UserData?>()
    var userNameData = MutableLiveData<String>()
    var passWordData = MutableLiveData<String>()

    fun queryByName(name : String){
        viewModelScope.launch(Dispatchers.IO) {
            var query: UserData? = UserDataBase.userDb.userDao.queryByUserName(name)
            queryData.postValue(query)
        }
    }

}