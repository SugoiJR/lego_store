package com.storeapp.lego.domain

import com.storeapp.lego.data.repository.LegoRepository
import com.storeapp.lego.domain.model.LegoModel
import com.storeapp.lego.utils.ResponseListState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LegoUseCase @Inject constructor(private val legoRepo: LegoRepository) {

    fun flowLego(): Flow<List<LegoModel>> {
        return legoRepo.legos
    }
    suspend fun addCart(items: List<LegoModel>) {
        return legoRepo.addCart(items)
    }

    suspend fun getAll(): ResponseListState<LegoModel> {
        return legoRepo.getAll()
    }

    fun logout() {
        return legoRepo.logout()
    }

    suspend fun deleteAll() {
        legoRepo.deleteAll()
    }
}