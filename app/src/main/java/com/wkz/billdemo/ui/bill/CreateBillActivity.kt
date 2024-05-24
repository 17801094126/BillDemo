package com.wkz.billdemo.ui.bill

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.wkz.billdemo.R
import com.wkz.billdemo.base.BaseActivity
import com.wkz.billdemo.data.BillData
import com.wkz.billdemo.data.BillDataBase
import com.wkz.billdemo.databinding.ActivityCreateBillBinding
import com.wkz.billdemo.ui.bill.viewmodel.CreateBillViewModel
import com.wkz.billdemo.utils.ToastUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.ceil

class CreateBillActivity : BaseActivity<ActivityCreateBillBinding,CreateBillViewModel>(R.layout.activity_create_bill) {

    private var type = 0 //消费类型   0代表现金   1代表信用卡  2代表花呗

    private var billZcOrSy = 0 //收入或者支出 1代表收入 2代表支出

    private var billData: BillData? = null
    private var isAdd = true
    
    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("yyyy-MM-dd")
    private val calendar = Calendar.getInstance()

    override fun createViewBinding(view: LayoutInflater): ActivityCreateBillBinding = ActivityCreateBillBinding.inflate(view)

    override fun createViewModel(): CreateBillViewModel = CreateBillViewModel()

    @SuppressLint("NewApi")
    override fun initView(inflateView: View?, savedInstanceState: Bundle?) {
       //默认为支出账单
        billZcOrSy = 2

        billData = intent.getParcelableExtra("item", BillData::class.java)
        billData?.let {
            isAdd = false
            binding.tvName.text = "编辑修改账单"
            binding.edCourseName.setText(it.billName)
            binding.edMoney.setText(it.billNum)
            binding.edLocation.setText(it.billBeiZhu)
            it.imgPath?.let {imgPath->
                Glide.with(this)
                    .load(imgPath)
                    .priority(Priority.LOW)
                    .into(binding.img)
            }?:{
                binding.img.setImageResource(R.mipmap.ic_launcher)
            }
            type = it.billType
        }?: kotlin.run{
            billData = BillData()
            isAdd = true
            binding.tvName.text = "添加账单"
        }
        when (type) {
            0 -> {
                val rbXJ = binding.rgType.getChildAt(1) as RadioButton
                rbXJ.setChecked(true)
            }

            1 -> {
                val rbXYK = binding.rgType.getChildAt(0) as RadioButton
                rbXYK.setChecked(true)
            }

            2 -> {
                val rbHB = binding.rgType.getChildAt(2) as RadioButton
                rbHB.setChecked(true)
            }
        }
        
    }

    override fun initListener() {
        super.initListener()
        binding.rgType.setOnCheckedChangeListener{ group: RadioGroup?, checkedId: Int->
            type = when(checkedId){
                R.id.rb_xyk -> 1
                R.id.rb_hb -> 2
                else -> 0
            }
        }

        binding.rgZhichu.setOnCheckedChangeListener{ group: RadioGroup?, checkedId: Int->
            billZcOrSy = when(checkedId){
                R.id.rb_zc -> 2
                else ->1
            }
        }
        
    }

    fun commit(v:View){
        val courseName: String = binding.edCourseName.getText().toString()
        val courseNum: String = binding.edMoney.getText().toString()
        val courseLocation: String = binding.edLocation.getText().toString()

        if (TextUtils.isEmpty(courseName)) {
            ToastUtils.showCustomCenterToast(this, "消费名称不能为空")
            return
        }
        if (TextUtils.isEmpty(courseNum)) {
            ToastUtils.showCustomCenterToast(this, "消费金额不能为空")
            return
        }

        billData?.let {
            if (isAdd) {
                it.billName = courseName
                it.billCreateTime = sdf.format(calendar.time)
                it.billUpdateTime = sdf.format(calendar.time)
                it.billNum = courseNum
                it.billType = type
                it.billZcOrSy = billZcOrSy
                it.billBeiZhu = courseLocation
                BillDataBase.billDb.billDao.insertUser(it)
                sendCustomBroadcast("添加数据成功")
            } else {
                it.billName = courseName
                it.billUpdateTime = sdf.format(calendar.time)
                it.billNum = courseNum
                it.billType = type
                it.billZcOrSy = billZcOrSy
                it.billBeiZhu = courseLocation
                BillDataBase.billDb.billDao.updateUser(it)
                sendCustomBroadcast("修改数据成功")
            } 
        }
        
        finish()
    }
    
    fun imgSelector(v:View){

        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, 2)
    }

    /**
     * 发送自定义的广播
     */
    private fun sendCustomBroadcast(message: String) {
        val intent = Intent()
        intent.putExtra("message", message)
        intent.setAction("com.android.example.CUSTOM_BROADCAST")
        sendBroadcast(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                Log.e(this.javaClass.getName(), "Result:$data")
                // 得到图片的全路径
                val uri = data.data
                try {
                    uri?.let {
                        val bitmap = loadImage(this, it)
                        bitmap?.let {bm->
                            saveBitmap(bm)
                            binding.img.setImageBitmap(bm)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap
     */
    fun saveBitmap(bitmap: Bitmap) {
        val filePic: File
        try {
            val imgPath = getNewPath(this, System.currentTimeMillis().toString() + ".jpg")
            billData?.imgPath = imgPath
            filePic = File(imgPath)
            if (!filePic.exists()) {
                filePic.getParentFile()?.mkdirs()
                filePic.createNewFile()
            }
            val fos = FileOutputStream(filePic)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getNewPath(context: Context, fileName: String): String {
        return context.filesDir.toString() + File.separator + fileName
    }

    @Throws(IOException::class)
    fun loadImage(context: Context, uri: Uri): Bitmap? {
        var input = context.contentResolver.openInputStream(uri)
        val opts = BitmapFactory.Options()
        opts.inJustDecodeBounds = true
        BitmapFactory.decodeStream(input, null, opts)
        input?.close()
        var height = opts.outHeight
        var width = opts.outWidth
        val windowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        if (width > height) {
            val t = width
            width = height
            height = t
        }
        val scaleX = ceil(1.0 * width / point.x).toInt()
        val scaleY = ceil(1.0 * height / point.y).toInt()
        var scale = 1
        if (scaleX > 1 && scaleX > scaleY) {
            scale = scaleX
        }
        if (scaleY > 1 && scaleY >= scaleX) {
            scale = scaleY
        }
        opts.inSampleSize = scale + 1
        opts.inJustDecodeBounds = false
        input = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(input, null, opts)
        input?.close()
        return bitmap
    }
}