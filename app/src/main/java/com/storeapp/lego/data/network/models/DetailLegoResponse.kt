package com.storeapp.lego.data.network.models

import com.google.gson.annotations.SerializedName
import com.storeapp.lego.domain.model.DetailLegoModel

data class DetailLegoResponse(
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val stock: Int,
    @SerializedName(value = "unit_price")
    val uniPrice: Int
)

fun DetailLegoResponse.toModel() = DetailLegoModel(
    description = description,
    id = id,
    image = image,
    name = name,
    stock = stock,
    uniPrice = uniPrice
)