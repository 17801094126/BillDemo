package com.wkz.billdemo.ui.bill

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.wkz.billdemo.R
import com.wkz.billdemo.adapter.BillAdapter
import com.wkz.billdemo.base.BaseActivity
import com.wkz.billdemo.data.BillData
import com.wkz.billdemo.data.BillDataBase
import com.wkz.billdemo.databinding.ActivityBillBinding
import com.wkz.billdemo.ui.bill.viewmodel.BillViewModel
import com.wkz.billdemo.utils.ToastUtils

class BillActivity : BaseActivity<ActivityBillBinding,BillViewModel>(R.layout.activity_bill) {

    private val mList = mutableListOf<BillData>()
    lateinit var courseAdapter: BillAdapter

    override fun createViewBinding(view: LayoutInflater): ActivityBillBinding = ActivityBillBinding.inflate(view)

    override fun createViewModel(): BillViewModel = BillViewModel()

    override fun initView(inflateView: View?, savedInstanceState: Bundle?) {

        binding.lv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        courseAdapter = BillAdapter(mList, this)
        binding.lv.adapter = courseAdapter

    }

    override fun initListener() {
        super.initListener()
        courseAdapter.setOnDeleteClickListener (object : BillAdapter.OnDeleteClickListener{
            override fun onDeleteClick(position: Int, courseData: BillData?) {
                courseData?.let {
                    showDeleteDialog(it)
                }
            }
        })
        courseAdapter.setOnItemAdapterClickListener(object : BillAdapter.OnItemAdapterClickListener{
            override fun onClick(position: Int, courseData: BillData) {
                startCreateBillActivity(courseData)
            }
        })
        binding.imgAdd.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CreateBillActivity::class.java
                )
            )
        }
    }

    private fun showDeleteDialog(courseData: BillData) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("是否删除")
        builder.setMessage("是否删除这条账单")
        builder.setNegativeButton(
            "取消"
        ) { dialog1: DialogInterface, which: Int -> dialog1.dismiss() }
        builder.setPositiveButton("确定") { dialog12: DialogInterface?, which: Int ->
            BillDataBase.billDb.billDao.deleteUser(courseData)
            ToastUtils.showCustomCenterToast(this, "删除数据成功")
            initData()
        }
        val dialog1 = builder.create()
        dialog1.show()
    }

    private fun startCreateBillActivity(courseData: BillData){
        val intent = Intent(this, CreateBillActivity::class.java)
        intent.putExtra("item", courseData)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        mViewModel.refreshData()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initData() {
        super.initData()
        mViewModel.refreshData()
        mViewModel.billListData.observe(this){
            it?.let {
                mList.clear()
                mList.addAll(it)
                courseAdapter.notifyDataSetChanged()
                if (it.isEmpty()) {
                    binding.lv.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                } else {
                    binding.lv.visibility = View.VISIBLE
                    binding.tvNoData.visibility = View.GONE
                }
            }
        }
    }

}