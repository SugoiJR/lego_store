package com.storeapp.lego.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.storeapp.lego.domain.model.ShoppingCarModel


@Entity
data class ShoppingCarEntity(
    @PrimaryKey
    val uid: Int,
    val id: Int,
    val image: String,
    val name: String,
    val stock: Int,
    @ColumnInfo(name = "unit_price")
    val unitPrice: Int
)

fun ShoppingCarEntity.toModel() = ShoppingCarModel(
    uid = uid,
    id = id,
    image = image,
    name = name,
    stock = stock,
    unitPrice = unitPrice,
)
