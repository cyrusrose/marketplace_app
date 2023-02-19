package com.it.access.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.it.access.data.mappers.Converter
import com.it.access.data.dao.ItemDao
import com.it.access.data.response.ItemResp

@TypeConverters(Converter::class)
@Database(entities = [ItemResp::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}