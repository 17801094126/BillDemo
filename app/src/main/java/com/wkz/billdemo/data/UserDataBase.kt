package com.wkz.billdemo.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wkz.billdemo.com.wkz.billdemo.BillApp
import com.wkz.billdemo.data.dao.UserDao

@Database(entities = [UserData::class], version = 1)
abstract class UserDataBase :RoomDatabase(){
    abstract val userDao : UserDao

    companion object{
        val userDb by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(BillApp.getContext(),UserDataBase::class.java,"USER.db").build()
        }

    }
}