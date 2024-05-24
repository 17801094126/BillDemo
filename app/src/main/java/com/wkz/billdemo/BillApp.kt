package com.wkz.billdemo.com.wkz.billdemo

import android.app.Application
import android.content.Context

class BillApp :Application() {

    companion object {
        lateinit var  context: Application
        fun getContext():Context{
            return context
        }

    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}