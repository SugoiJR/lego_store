package com.storeapp.lego.domain.model

data class DetailLegoModel(
    val description: String = "",
    val id: Int = 0,
    val image: String = "",
    val name: String = "",
    val stock: Int = 0,
    val uniPrice: Int = 0
)