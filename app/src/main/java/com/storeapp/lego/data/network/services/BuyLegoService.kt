package com.storeapp.lego.data.network.services

import com.storeapp.lego.data.network.ApiClient
import com.storeapp.lego.utils.ResState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BuyLegoService @Inject constructor(private val apiClient: ApiClient) {

    suspend operator fun invoke(): ResState<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val res = apiClient.buy()
                val body = res.body()

                when (body != null) {
                    true -> {
                        ResState.Success(true)
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