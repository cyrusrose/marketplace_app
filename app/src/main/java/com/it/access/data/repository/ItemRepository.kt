package com.it.access.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class ItemRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getAll() = flowOf(
        items
    )
        .flowOn(dispatcher)
}