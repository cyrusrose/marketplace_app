package com.it.access.data.repository

import com.it.access.data.dao.ItemDao
import com.it.access.data.response.ItemResp
import com.it.access.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ItemRepository(
    private val dao: ItemDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getAll() = dao.getAll().flowOn(dispatcher)

    suspend fun deleteAll(items: List<ItemResp>) = withContext(dispatcher) {
        dao.deleteAll(items)
    }

    suspend fun insertAll(items: List<ItemResp>) = withContext(dispatcher) {
        dao.insertAll(items)
    }
}