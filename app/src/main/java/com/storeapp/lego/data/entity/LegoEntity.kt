package com.storeapp.lego.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.storeapp.lego.domain.model.LegoModel


@Entity
data class LegoEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val id: Int,
    val image: String,
    val name: String,
    var stock: Int,
    @ColumnInfo(name = "unit_price")
    val unitPrice: Int
)

fun LegoEntity.toModel() = LegoModel(
    uid = uid,
    id = id,
    image = image,
    name = name,
    stock = stock,
    unitPrice = unitPrice,
)