package com.it.access.data.response

import java.math.BigDecimal

data class ItemResp(
    val id: Int,
    val title: String,
    val uri: String,
    val type: String,
    val location: String,
    val surface: Int,
    val power: Int,
    val element: String,
    val price: BigDecimal,
    val speed: Int,
    val color: String,
    val length: Int,
    val width: Int,
    val height: Int,
    val weight: BigDecimal,
    val functions: Set<String>
) {
    fun toList() = listOf(
        "Type: $type",
        "Location: $location",
        "Surface: $surface м²",
        "Power: $power вт",
        "Heating element: $element",
        "Price: $price руб",
        "Gears: $speed",
        "Color: $color",
        "Length: $length см",
        "Width: $width см",
        "Height: $height см",
        "Functions:",
        *functions.toTypedArray(),
        "Weight: $weight кг"
    )
}