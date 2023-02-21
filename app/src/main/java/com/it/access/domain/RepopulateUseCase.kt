package com.it.access.domain

import com.it.access.data.repository.ItemRepository
import com.it.access.data.response.ItemResp
import javax.inject.Inject

class RepopulateUseCase @Inject constructor(private val rep: ItemRepository) {
    suspend operator fun invoke(items: List<ItemResp>) {
        rep.deleteAll(items)
        rep.insertAll(items)
    }
}