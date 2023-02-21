package com.it.access.domain

import java.math.BigDecimal

data class SearchState(
    val type: List<TypeParam>? = null,
    val location: List<LocationParam>? = null,
    val surface: List<SurfaceParam>? = null,
    val power: List<PowerParam>? = null,
    val element: List<ElementParam>? = null,
    val price: DecimalParam? = null,
    val speed: List<SpeedParam>? = null,
    val color: List<ColorParam>? = null,
    val length: IntParam? = null,
    val width: IntParam? = null,
    val height: IntParam? = null,
    val weight: DecimalParam? = null,
    val isRemote: Boolean? = null,
    val isHeatingProtected: Boolean? = null,
    val isRolloverProtected: Boolean? = null
)


enum class TypeParam(val type: String) {
    FAN("тепловентилятор"),
    OIL("масляный обогреватель"),
    TUBE("трубчатый")
}

enum class LocationParam(val type: String) {
    FLOOR("напольный"),
    WALL("настенный")
}

enum class SurfaceParam(
    val type: String
) {
    SMALL("< 20 м²"),
    MEDIUM("≥ 20 м²");

    fun check(item: Int): Boolean {
        return if (this == SMALL)
            item < 20
        else
            item >= 20
    }
}

enum class PowerParam(val type: String) {
    SMALL("< 800 вт"),
    MEDIUM("850-1199 вт"),
    LARGE("1200-2000 вт"),
    EXTRA("> 2000 вт");

    fun check(item: Int): Boolean {
        return when (this) {
            SMALL -> item < 800
            MEDIUM -> item >= 850 && item <= 1199
            LARGE -> item >= 1200 && item <= 2000
            EXTRA -> item > 2000
        }
    }
}

enum class ElementParam(val type: String) {
    PTC("PTC"),
    WIRE("нагревательный провод"),
    FIBER("углеродное волокно")
}

enum class SpeedParam(val type: Int) {
    TWO(2),
    THREE(3),
    FOUR(4)
}

enum class ColorParam(val type: String) {
    WHITE("белый"),
    BLACK("черный")
}

data class Params<T>(val from: T, val to: T)

typealias DecimalParam = Params<BigDecimal>

typealias IntParam = Params<Int>