package com.it.access.domain.use_cases

import com.it.access.domain.SearchState
import com.it.access.presentation.Types
import javax.inject.Inject

class FilterCheckBoxUseCase @Inject constructor() {
    operator fun invoke(item: SearchState, isChecked: Boolean, value: String, type: Types) =
        when(type) {
            Types.TYPE -> item.copy(type = if(isChecked) item.type + value else item.type - value)
            Types.SURFACE -> item.copy(surface = if(isChecked) item.surface + value else item.surface - value)
            Types.LOCATION -> item.copy(location = if(isChecked) item.location + value else item.location - value)
            Types.POWER -> item.copy(power = if(isChecked) item.power + value else item.power - value)
            Types.ELEMENT -> item.copy(element = if(isChecked) item.element + value else item.element - value)
            Types.SPEED -> item.copy(speed = if(isChecked) item.speed + value else item.speed - value)
            Types.COLOR -> item.copy(color = if(isChecked) item.color + value else item.color - value)
            Types.FUNCTIONS -> item.copy(functions = if(isChecked) item.functions + value else item.functions - value)
            else -> item
        }
}