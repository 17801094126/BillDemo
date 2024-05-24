package com.wkz.billdemo.ui.bill.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wkz.billdemo.base.BaseViewModel
import com.wkz.billdemo.data.BillData
import com.wkz.billdemo.data.BillDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BillViewModel : BaseViewModel() {

    var billListData = MutableLiveData<MutableList<BillData>>()

    fun refreshData(){
        viewModelScope.launch(Dispatchers.IO) {
            val queryAll = BillDataBase.billDb.billDao.queryAll()
            billListData.postValue(queryAll as MutableList<BillData>?)
        }

    }
}