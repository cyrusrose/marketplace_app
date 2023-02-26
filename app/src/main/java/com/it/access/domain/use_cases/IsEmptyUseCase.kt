package com.it.access.domain.use_cases

import com.it.access.domain.SearchState
import javax.inject.Inject

class IsEmptyUseCase @Inject constructor() {
    operator fun invoke(item: SearchState) =
        item.type.isEmpty() && item.location.isEmpty()
        && item.surface.isEmpty() && item.power.isEmpty()
        && item.element.isEmpty() && item.speed.isEmpty()
        && item.color.isEmpty() && item.functions.isEmpty()
        && item.price.isEmpty() && item.length.isEmpty()
        && item.width.isEmpty() && item.height.isEmpty()
        && item.weight.isEmpty()
}