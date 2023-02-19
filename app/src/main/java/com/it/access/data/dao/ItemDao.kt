package com.it.access.data.dao

import androidx.room.*
import com.it.access.data.response.ItemResp
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): Flow<List<ItemResp>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<ItemResp>)

    @Delete
    suspend fun deleteAll(item: List<ItemResp>)
}