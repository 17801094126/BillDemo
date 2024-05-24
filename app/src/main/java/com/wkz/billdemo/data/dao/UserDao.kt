package com.wkz.billdemo.data.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wkz.billdemo.data.UserData

@Dao
interface UserDao {
    /**
     * 增
     */
    @Insert
    fun insertUser(userData: UserData)

    /**
     * 删除
     */
    @Delete
    fun deleteUser(userData: UserData)

    /**
     * 修改
     */
    @Update
    fun updateUser(userData: UserData)

    /**
     * 查询所有
     */
    @Query("select * from USER")
    fun queryAll():List<UserData>?

    /**
     * 根据名称进行查询
     */
    @Query("select * from USER where username == :username")
    fun queryByUserName(username:String):UserData?
}