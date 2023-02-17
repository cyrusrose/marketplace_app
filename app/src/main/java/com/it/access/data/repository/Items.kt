package com.it.access.data.repository

import com.it.access.data.response.Item
import java.math.BigDecimal

val items = listOf(
    Item(
        id = 0,
        uri = "file:///android_asset/images/fan_heater_1",
        type = "тепловентилятор",
        location = "напольный",
        surface = 20,
        power = 220,
        element = "PTC",
        price = BigDecimal("123.00").setScale(2),
        speed = 2,
        color = "белый",
        length = 2,
        width = 2,
        height = 2,
        weight = BigDecimal("10.01").setScale(2)
    ),
    Item(
        id = 1,
        uri = "file:///android_asset/images/fan_heater_1",
        type = "тепловентилятор",
        location = "напольный",
        surface = 20,
        power = 220,
        element = "PTC",
        price = BigDecimal("123.00").setScale(2),
        speed = 2,
        color = "белый",
        length = 2,
        width = 2,
        height = 2,
        weight = BigDecimal("10.01").setScale(2)
    )
)