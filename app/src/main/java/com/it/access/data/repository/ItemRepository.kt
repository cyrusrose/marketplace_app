package com.it.access.data.repository

import android.app.Application
import com.it.access.data.dao.ItemDao
import com.it.access.data.response.Item
import com.it.access.util.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository(val dao: ItemDao, val dispatcher: CoroutineDispatcher = Dispatchers.IO) {
    suspend fun getAll() = withContext(dispatcher) {
        Response.Success(dao.getAll())
    }

    suspend fun deleteAll(items: List<Item>) = withContext(dispatcher) {
        dao.deleteAll(items)
    }

    suspend fun insertAll(items: List<Item>) = withContext(dispatcher) {
        dao.insertAll(items)
    }
}