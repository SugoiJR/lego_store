package com.storeapp.lego.data.network.services

import com.storeapp.lego.data.network.ApiClient
import com.storeapp.lego.data.network.models.toModel
import com.storeapp.lego.domain.model.LegoModel
import com.storeapp.lego.utils.ResponseListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AllProductsLegoService @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getAll(): ResponseListState<LegoModel> {
        return withContext(Dispatchers.IO) {
            try {
                val res = apiClient.allProducts()
                val body = res.body()

                when (body != null) {
                    true -> {
                        val data = body.products.map { it.toModel() }
                        ResponseListState.Success(data)
                    }

                    false -> ResponseListState.Error(
                        "Service error",
                        Exception(res.code().toString())
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ResponseListState.Error("Connection failed", e)
            }
        }
    }

}