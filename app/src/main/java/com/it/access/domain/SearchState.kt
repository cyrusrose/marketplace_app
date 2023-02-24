package com.it.access.domain

import androidx.annotation.IdRes
import com.it.access.R
import java.math.BigDecimal

data class SearchState(
    val type: List<String>? = null,
    val location: List<String>? = null,
    val surface: List<String>? = null,
    val power: List<String>? = null,
    val element: List<String>? = null,
    val price: DecimalParam = Params(),
    val speed: List<String>? = null,
    val color: List<String>? = null,
    val length: IntParam = Params(),
    val width: IntParam = Params(),
    val height: IntParam = Params(),
    val weight: DecimalParam = Params(),
    val isRemote: Boolean? = null,
    val isHeatingProtected: Boolean? = null,
    val isRolloverProtected: Boolean? = null
)


fun check(name: String, item: Int): Boolean =
    when (name) {
        "< 20 м²" -> item < 20
        "≥ 20 м²" -> item >= 20
        "< 800 вт" -> item < 800
        "850–1199 вт" -> item in 850..1199
        "1200–2000 вт" -> item in 1200..2000
        "> 2000 вт" -> item > 2000
        else -> false
    }


data class Params<T>(val from: T? = null, val to: T? = null)

typealias DecimalParam = Params<BigDecimal>

typealias IntParam = Params<Int>