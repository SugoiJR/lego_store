package com.storeapp.lego.data.network.services

import com.storeapp.lego.data.network.ApiClient
import com.storeapp.lego.data.network.models.toModel
import com.storeapp.lego.domain.model.DetailLegoModel
import com.storeapp.lego.utils.ResState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailLegoService @Inject constructor(private val apiClient: ApiClient) {
    suspend fun getDetail(id: String): ResState<DetailLegoModel> {
        return withContext(Dispatchers.IO) {
            try {
                val res = apiClient.legoDetail(id)
                val body = res.body()

                when (body != null) {
                    true -> {
                        ResState.Success(body.toModel())
                    }

                    false -> ResState.Error(
                        "Service error",
                        Exception(res.code().toString())
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ResState.Error("Connection failed", e)
            }
        }
    }
}