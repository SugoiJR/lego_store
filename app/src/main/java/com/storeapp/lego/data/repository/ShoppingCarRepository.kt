package com.storeapp.lego.data.repository

import com.storeapp.lego.data.dao.LegoDao
import com.storeapp.lego.data.dao.ShoppingCarDao
import com.storeapp.lego.data.entity.toModel
import com.storeapp.lego.data.network.services.BuyLegoService
import com.storeapp.lego.domain.model.LegoModel
import com.storeapp.lego.domain.model.ShoppingCarModel
import com.storeapp.lego.domain.model.toCarEntity
import com.storeapp.lego.utils.ResState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingCarRepository @Inject constructor(
    private val buyLegoService: BuyLegoService,
    private val shoppingCarDao: ShoppingCarDao,
    private val legoDao: LegoDao
) {

    val itemCarts: Flow<List<ShoppingCarModel>> = shoppingCarDao.getItemsCart().map {
        it.map { item -> item.toModel() }
    }

    suspend fun addAtCar(legoModel: LegoModel) {
        withContext(Dispatchers.IO) {
            if (legoModel.stock > 0) {
                //If exist: update, else insert
                val findItem = shoppingCarDao.findLego(legoModel.uid)
                when {
                    findItem != null -> {
                        if (legoModel.stock > findItem.stock) {
                            shoppingCarDao.upSert(
                                findItem.copy(
                                    stock = findItem.stock + 1
                                )
                            )
                        }
                    }

                    else -> shoppingCarDao.upSert(
                        legoModel.copy(
                            stock = 1
                        ).toCarEntity()
                    )
                }
            }
        }
    }

    suspend fun buy(): ResState<Boolean> {
        return buyLegoService()
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            shoppingCarDao.deleteAll()
        }
    }

    suspend fun updateLegos(legos: List<ShoppingCarModel>): ResState<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val list = legoDao.getLegosByIds(legos.map { it.uid })
                legos.forEach { item ->
                    val find = list.first { it.uid == item.uid }
                    find.stock -= item.stock
                    legoDao.updateLego(find)
                }
                shoppingCarDao.deleteAll()
                ResState.Success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                ResState.Error("Fail buy lego", e)
            }
        }
    }
}