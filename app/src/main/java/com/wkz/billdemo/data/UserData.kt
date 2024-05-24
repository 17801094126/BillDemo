package com.wkz.billdemo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Entity 是声明数据库表
 * tableName 声明表名
 */
@Entity(tableName = "USER")
class UserData {

    //用户ID
    @PrimaryKey(autoGenerate = true)
    var id = 0

    //账号
    @ColumnInfo(name = "username")
    var userName: String? = null

    //密码
    @ColumnInfo(name = "password")
    var passWord: String? = null

}