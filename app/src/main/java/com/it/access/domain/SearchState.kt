package com.it.access.domain

import androidx.annotation.IdRes
import com.it.access.R
import java.math.BigDecimal

data class SearchState(
    val type: Set<String> = setOf(),
    val location: Set<String> = setOf(),
    val surface: Set<String> = setOf(),
    val power: Set<String> = setOf(),
    val element: Set<String> = setOf(),
    val price: DecimalParam = Params(),
    val speed: Set<String> = setOf(),
    val color: Set<String> = setOf(),
    val length: IntParam = Params(),
    val width: IntParam = Params(),
    val height: IntParam = Params(),
    val weight: DecimalParam = Params(),
    val functions: Set<String> = setOf()
)


fun check(name: String, item: Int): Boolean =
    when (name) {
        "< 20 m²" -> item < 20
        "≥ 20 m²" -> item >= 20
        "< 800 W" -> item < 800
        "850–1199 W" -> item in 850..1199
        "1200–2000 W" -> item in 1200..2000
        "> 2000 W" -> item > 2000
        else -> false
    }


data class Params<T>(val from: T? = null, val to: T? = null) {
    fun isEmpty() = from == null && to == null
}

typealias DecimalParam = Params<BigDecimal>

typealias IntParam = Params<Int>