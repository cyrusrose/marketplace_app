package com.it.access.data.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "item")
data class ItemResp(
    @PrimaryKey val id: Int,
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
    @ColumnInfo(name = "is_remote") val isRemote: Boolean = false,
    @ColumnInfo(name = "is_heating_protected") val isHeatingProtected: Boolean = false,
    @ColumnInfo(name = "is_rollover_protected") val isRolloverProtected: Boolean = false
) {
    fun toList() =
        listOf(
            "Тип: $type",
            "Размещение: $location",
            "Обогреваемая площадь: м^2"
        )
}