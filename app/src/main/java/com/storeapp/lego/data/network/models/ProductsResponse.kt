package com.storeapp.lego.data.network.models

import com.google.gson.annotations.SerializedName
import com.storeapp.lego.domain.model.LegoModel

data class ProductsResponse(
    val products: List<LegoRes>
)

data class LegoRes(
    val id: Int,
    val image: String,
    val name: String,
    val stock: Int,
    @SerializedName(value = "unit_price")
    val unitPrice: Int
)

fun LegoRes.toModel() = LegoModel(
    id = id,
    image = image,
    name = name,
    stock = stock,
    unitPrice = unitPrice,
)