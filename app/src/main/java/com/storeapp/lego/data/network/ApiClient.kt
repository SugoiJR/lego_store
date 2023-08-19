package com.storeapp.lego.data.network

import com.storeapp.lego.data.network.models.DetailLegoResponse
import com.storeapp.lego.data.network.models.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {
    @GET("allProducts")
    suspend fun allProducts(): Response<ProductsResponse>

    @POST("buy")
    suspend fun buy(): Response<ProductsResponse>

    @GET("detail")
    suspend fun legoDetail(@Query("id") id: String): Response<DetailLegoResponse>
}