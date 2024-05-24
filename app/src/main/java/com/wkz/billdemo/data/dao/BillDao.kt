package com.wkz.billdemo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wkz.billdemo.data.BillData
import com.wkz.billdemo.data.UserData

@Dao
interface BillDao {

    /**
     * 增
     */
    @Insert
    fun insertUser(billData: BillData)

    /**
     * 删除
     */
    @Delete
    fun deleteUser(billData: BillData)

    /**
     * 修改
     */
    @Update
    fun updateUser(billData: BillData)

    /**
     * 查询所有
     */
    @Query("select * from BILL")
    fun queryAll():List<BillData>?

    /**
     * 根据名称进行查询
     */
    @Query("select * from BILL where billName == :billName")
    fun queryByUserName(billName:String): UserData?
}