package com.wkz.billdemo.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BILL")
class BillData() :Parcelable {
    //账单ID
    @PrimaryKey(autoGenerate = true)
    var id = 0

    //消费名称
    @ColumnInfo(name = "billName")
    var billName: String? = null

    @ColumnInfo(name = "billType")
    var billType = 0 //消费类型  0代表现金   1代表信用卡  2代表花呗
    @ColumnInfo(name = "billZcOrSy")
    var billZcOrSy = 0 //收入或者支出 1代表收入 2代表支出

    @ColumnInfo(name = "billCreateTime")
    var billCreateTime: String? = null //账单创建时间
    @ColumnInfo(name = "billUpdateTime")
    var billUpdateTime: String? = null //账单修改时间
    @ColumnInfo(name = "billNum")
    var billNum: String? = null //消费金额
    @ColumnInfo(name = "billBeiZhu")
    var billBeiZhu: String? = null //消费备注
    @ColumnInfo(name = "billImgPath")
    var imgPath: String? = null //课程图片

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        billName = parcel.readString()
        billType = parcel.readInt()
        billZcOrSy = parcel.readInt()
        billCreateTime = parcel.readString()
        billUpdateTime = parcel.readString()
        billNum = parcel.readString()
        billBeiZhu = parcel.readString()
        imgPath = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(billName)
        parcel.writeInt(billType)
        parcel.writeInt(billZcOrSy)
        parcel.writeString(billCreateTime)
        parcel.writeString(billUpdateTime)
        parcel.writeString(billNum)
        parcel.writeString(billBeiZhu)
        parcel.writeString(imgPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BillData> {
        override fun createFromParcel(parcel: Parcel): BillData {
            return BillData(parcel)
        }

        override fun newArray(size: Int): Array<BillData?> {
            return arrayOfNulls(size)
        }
    }
}