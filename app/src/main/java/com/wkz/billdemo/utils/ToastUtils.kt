package com.wkz.billdemo.utils

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.wkz.billdemo.R

class ToastUtils private constructor(){

    init {
        throw AssertionError()
    }


    companion object{

        @JvmStatic
        fun showCustomToast(context: Context?, text: String?) {
            if (context == null) {
                return
            }
            if (TextUtils.isEmpty(text)) return
            val inflater = LayoutInflater.from(context)
            val toastView: View =
                inflater.inflate(R.layout.layout_toast, null) // 用布局解析器来解析一个布局去适配Toast的setView方法
            val tvText = toastView.findViewById<TextView>(R.id.tv_text)
            tvText.text = text
            val toast = Toast(context)
            toast.view = toastView
            toast.setDuration(Toast.LENGTH_SHORT)
            toast.show()
        }
        @JvmStatic
        fun showCustomToast(context: Context?, text: String?, time: Int) {
            if (context == null) {
                return
            }
            if (TextUtils.isEmpty(text)) return
            val inflater = LayoutInflater.from(context)
            val toastView: View =
                inflater.inflate(R.layout.layout_toast, null) // 用布局解析器来解析一个布局去适配Toast的setView方法
            val tvText = toastView.findViewById<TextView>(R.id.tv_text)
            tvText.text = text
            val toast = Toast(context)
            toast.view = toastView
            toast.setDuration(time)
            toast.show()
        }
        @JvmStatic
        fun showCustomCenterToast(context: Context?, text: String?) {
            if (context == null) {
                return
            }
            if (TextUtils.isEmpty(text)) return
            val inflater = LayoutInflater.from(context)
            val toastView: View =
                inflater.inflate(R.layout.layout_toast, null) // 用布局解析器来解析一个布局去适配Toast的setView方法
            val tvText = toastView.findViewById<TextView>(R.id.tv_text)
            tvText.text = text
            val toast = Toast(context)
            toast.setView(toastView)
            toast.setDuration(Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        @JvmStatic
        fun showCustomCenterToast(context: Context?, text: String?, time: Int) {
            if (context == null) {
                return
            }
            if (TextUtils.isEmpty(text)) return
            val inflater = LayoutInflater.from(context)
            val toastView: View =
                inflater.inflate(R.layout.layout_toast, null) // 用布局解析器来解析一个布局去适配Toast的setView方法
            val tvText = toastView.findViewById<TextView>(R.id.tv_text)
            tvText.text = text
            val toast = Toast(context)
            toast.setView(toastView)
            toast.setDuration(time)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }

        @JvmStatic
        fun showCustomCenterToast(context: Context?, text: String?, xOffSet: Int, yOffset: Int) {
            if (context == null) {
                return
            }
            if (TextUtils.isEmpty(text)) return
            val inflater = LayoutInflater.from(context)
            val toastView: View =
                inflater.inflate(R.layout.layout_toast, null) // 用布局解析器来解析一个布局去适配Toast的setView方法
            val tvText = toastView.findViewById<TextView>(R.id.tv_text)
            tvText.text = text
            val toast = Toast(context)
            toast.setView(toastView)
            toast.setDuration(Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, xOffSet, yOffset)
            toast.show()
        }
    }


}