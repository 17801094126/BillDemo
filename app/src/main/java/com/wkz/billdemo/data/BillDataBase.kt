package com.wkz.billdemo.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wkz.billdemo.com.wkz.billdemo.BillApp
import com.wkz.billdemo.data.dao.BillDao
import com.wkz.billdemo.data.dao.UserDao


@Database(entities = [BillData::class], version = 1)
abstract class BillDataBase : RoomDatabase(){
    abstract val billDao : BillDao

    companion object{
        val billDb by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(BillApp.getContext(),BillDataBase::class.java,"BILL.db")
                .allowMainThreadQueries()
                .build()
        }

    }
}
