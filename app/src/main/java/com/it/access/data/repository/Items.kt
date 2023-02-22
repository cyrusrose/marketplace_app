package com.it.access.data.repository

import com.it.access.data.response.ItemResp
import com.it.access.domain.*
import java.math.BigDecimal

val items = listOf(
    ItemResp(
        id = 0,
        title = "Электрический обогреватель MEXI 910744156",
        uri = "file:///android_asset/images/fan_heater_1.jpg",
        type = "тепловентилятор",
        location = "напольный",
        surface = 20,
        power = 1500,
        element = "PTC",
        price = BigDecimal("1255.47").setScale(2),
        speed = 3,
        color = "белый",
        length = 15,
        width = 15,
        height = 15,
        weight = BigDecimal("1.15").setScale(2),
        isHeatingProtected = true
    ),
    ItemResp(
        id = 1,
        title = "Электрический обогреватель ATWFS heating",
        uri = "file:///android_asset/images/fan_heater_2.jpg",
        type = "тепловентилятор",
        location = "напольный",
        surface = 30,
        power = 2000,
        element = "PTC",
        price = BigDecimal("3660.04").setScale(2),
        speed = 4,
        color = "черный",
        length = 25,
        width = 20,
        height = 38,
        weight = BigDecimal("1.10").setScale(2)
    ),
    ItemResp(
        id = 2,
        title = "Электрический обогреватель ATWFS electric heater",
        uri = "file:///android_asset/images/fan_heater_3.jpg",
        type = "тепловентилятор",
        location = "напольный",
        surface = 40,
        power = 1800,
        element = "PTC",
        price = BigDecimal("4991.60").setScale(2),
        speed = 3,
        color = "черный",
        length = 22,
        width = 14,
        height = 45,
        weight = BigDecimal("2.01").setScale(2),
        isHeatingProtected = true,
        isRolloverProtected = true,
        isRemote = true
    )
)