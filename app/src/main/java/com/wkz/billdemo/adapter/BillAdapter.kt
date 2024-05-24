package com.wkz.billdemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.wkz.billdemo.R
import com.wkz.billdemo.data.BillData
import com.wkz.billdemo.databinding.ItemOsBinding

class BillAdapter(courseData:MutableList<BillData>,context: Context) : RecyclerView.Adapter<BillAdapter.BillHolder>() {

    private var courseData: MutableList<BillData>? = null
    private lateinit var context: Context

    init {
        this.courseData = courseData
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillHolder {
        return BillHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_os, parent, false))
    }

    override fun getItemCount(): Int = courseData!!.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BillHolder, position: Int) {
        var course : BillData? =null
        courseData?.let {
            course = it[position]
        }

        course?.let {
            holder.binding.tvWeek.text = "创建时间 : ${it.billCreateTime}"
            holder.binding.tvTime.text = "修改时间 : ${it.billUpdateTime}"

            if (it.billZcOrSy == 1){
                //收入
                holder.binding.tvCourseName.text = "收入名称 : " + if (TextUtils.isEmpty(it.billName)) "" else it.billName
                holder.binding.tvCouseNum.text = "收入金额 : + ${it.billNum}元"
                holder.binding.tvCouseNum.setTextColor(ContextCompat.getColor(
                    context,
                    R.color.green_percent_2
                ))
                when (it.billType) {
                    0 -> holder.binding.tvCourseTeacher.text = "收入类别 : 人民币"
                    1 -> holder.binding.tvCourseTeacher.text = "收入类别 : 信用卡"
                    2 -> holder.binding.tvCourseTeacher.text = "收入类别 : 手机支付"
                }
            }else{
                holder.binding.tvCourseName.text = "消费名称 : " + if (TextUtils.isEmpty(it.billName)) "" else it.billName
                holder.binding.tvCouseNum.text = "消费金额 : - ${it.billNum}元"
                holder.binding.tvCouseNum.setTextColor(ContextCompat.getColor(context, R.color.market_red))

                when (it.billType) {
                    0 -> holder.binding.tvCourseTeacher.text = "消费类别 : 人民币"
                    1 -> holder.binding.tvCourseTeacher.text = "消费类别 : 信用卡"
                    2 -> holder.binding.tvCourseTeacher.text = "消费类别 : 手机支付"
                }
            }

            if (TextUtils.isEmpty(it.billBeiZhu)) {
                holder.binding.tvLocation.visibility = View.GONE
            } else {
                holder.binding.tvLocation.visibility = View.VISIBLE
                holder.binding.tvLocation.text = "文字备注 : ${it.billBeiZhu}"
            }

            if (!TextUtils.isEmpty(it.imgPath)) {
                holder.binding.img.visibility = View.VISIBLE
                Glide.with(context)
                    .load(it.imgPath)
                    .priority(Priority.LOW)
                    .into(holder.binding.img)
            } else {
                holder.binding.img.visibility = View.GONE
            }
        }
        holder.binding.btDelete.setOnClickListener {
            onDeleteClickListener?.onDeleteClick(
                position,
                course
            )
        }
        holder.binding.root.setOnClickListener {
            onItemAdapterClickListener?.onClick(
                position,
                course!!
            )
        }

        if (position == courseData!!.size - 1) {
            holder.binding.viewLine.visibility = View.GONE
        } else {
            holder.binding.viewLine.visibility = View.VISIBLE
        }
    }

    class BillHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var binding: ItemOsBinding
        init {
            binding = ItemOsBinding.bind(itemView)
        }
    }

    private var onItemAdapterClickListener: OnItemAdapterClickListener? = null


    fun setOnItemAdapterClickListener(onItemAdapterClickListener: OnItemAdapterClickListener?) {
        this.onItemAdapterClickListener = onItemAdapterClickListener
    }


    interface OnItemAdapterClickListener {
        fun onClick(position: Int, courseData: BillData)
    }


    private var onDeleteClickListener: OnDeleteClickListener? = null

    fun setOnDeleteClickListener(onDeleteClickListener: OnDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener
    }


    interface OnDeleteClickListener {
        fun onDeleteClick(position: Int, courseData: BillData?)
    }

}