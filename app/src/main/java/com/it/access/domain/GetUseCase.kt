package com.it.access.domain

import com.it.access.data.repository.ItemRepository
import javax.inject.Inject

class GetUseCase @Inject constructor(
    private val rep: ItemRepository
) {
    operator fun invoke() = rep.getAll()
}