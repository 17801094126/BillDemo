package com.wkz.billdemo.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    inner class UiLoadingChange {

    }

}