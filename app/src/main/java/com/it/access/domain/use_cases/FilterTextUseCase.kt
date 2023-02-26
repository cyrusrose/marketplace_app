package com.it.access.domain.use_cases

import com.it.access.domain.SearchState
import com.it.access.presentation.Types
import com.it.access.util.SCALE
import javax.inject.Inject

class FilterTextUseCase @Inject constructor() {
    operator fun invoke(item: SearchState, param: String, type: Types) =
        when(type) {
            Types.PRICE_FROM ->
                item.copy(price = item.price.copy(
                    from = param.toBigDecimalOrNull()?.setScale(SCALE)
                ))
            Types.PRICE_TO ->
                item.copy(price = item.price.copy(
                    to = param.toBigDecimalOrNull()?.setScale(SCALE)
                ))
            Types.LENGTH_FROM ->
                item.copy(length = item.length.copy(
                    from = param.toIntOrNull()
                ))
            Types.LENGTH_TO ->
                item.copy(length = item.length.copy(
                    to = param.toIntOrNull()
                ))
            Types.WIDTH_FROM ->
                item.copy(width = item.width.copy(
                    from = param.toIntOrNull()
                ))
            Types.WIDTH_TO ->
                item.copy(width = item.width.copy(
                    to = param.toIntOrNull()
                ))
            Types.HEIGHT_FROM ->
                item.copy(height = item.height.copy(
                    from = param.toIntOrNull()
                ))
            Types.HEIGHT_TO ->
                item.copy(height = item.height.copy(
                    to = param.toIntOrNull()
                ))
            Types.WEIGHT_FROM ->
                item.copy(weight = item.weight.copy(
                    from = param.toBigDecimalOrNull()?.setScale(SCALE)
                ))
            Types.WEIGHT_TO ->
                item.copy(weight = item.weight.copy(
                    to = param.toBigDecimalOrNull()?.setScale(SCALE)
                ))
            else -> item
        }
}