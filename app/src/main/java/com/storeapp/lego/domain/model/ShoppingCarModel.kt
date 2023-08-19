package com.storeapp.lego.domain.model

import com.storeapp.lego.data.entity.ShoppingCarEntity

data class ShoppingCarModel(
    val uid: Int = 0,
    val id: Int = 0,
    val image: String = "",
    val name: String = "",
    val stock: Int = 0,
    val unitPrice: Int = 0
)

fun ShoppingCarModel.toEntity() = ShoppingCarEntity(
    uid = uid,
    id = id,
    image = image,
    name = name,
    stock = stock,
    unitPrice = unitPrice,
)
