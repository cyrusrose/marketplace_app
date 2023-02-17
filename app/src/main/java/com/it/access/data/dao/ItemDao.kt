package com.it.access.data.dao

import androidx.room.*
import com.it.access.data.response.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    suspend fun getAll(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Item>)

    @Delete
    suspend fun deleteAll(item: List<Item>)
}