package com.storeapp.lego.domain.model

import com.storeapp.lego.data.entity.LegoEntity
import com.storeapp.lego.data.entity.ShoppingCarEntity

data class LegoModel(
    val uid: Int = 0,
    val id: Int = 0,
    val image: String = "",
    val name: String = "",
    val stock: Int = 0,
    val unitPrice: Int = 0
)

fun LegoModel.toEntity() = LegoEntity(
    uid = uid,
    id = id,
    image = image,
    name = name,
    stock = stock,
    unitPrice = unitPrice,
)

fun LegoModel.toCarEntity() = ShoppingCarEntity(
    uid = uid,
    id = id,
    image = image,
    name = name,
    stock = stock,
    unitPrice = unitPrice,
)
