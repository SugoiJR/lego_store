package com.storeapp.lego.domain

import com.storeapp.lego.data.repository.ShoppingCarRepository
import com.storeapp.lego.domain.model.LegoModel
import com.storeapp.lego.domain.model.ShoppingCarModel
import com.storeapp.lego.utils.ResState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoppingCarUseCase @Inject constructor(
    private val shoppingCarRepository: ShoppingCarRepository
) {
    fun flowCart(): Flow<List<ShoppingCarModel>> {
        return shoppingCarRepository.itemCarts
    }
    suspend fun addToCar(legoModel: LegoModel) {
        return shoppingCarRepository.addAtCar(legoModel)
    }
    suspend fun buy(): ResState<Boolean> {
        return shoppingCarRepository.buy()
    }
    suspend fun deleteAll() {
        shoppingCarRepository.deleteAll()
    }

    suspend fun updateLegos(legos: List<ShoppingCarModel>): ResState<Boolean> {
        return shoppingCarRepository.updateLegos(legos)
    }
}