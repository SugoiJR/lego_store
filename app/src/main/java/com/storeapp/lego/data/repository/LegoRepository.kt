package com.storeapp.lego.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.storeapp.lego.data.dao.LegoDao
import com.storeapp.lego.data.entity.toModel
import com.storeapp.lego.data.network.services.AllProductsLegoService
import com.storeapp.lego.domain.model.LegoModel
import com.storeapp.lego.domain.model.toEntity
import com.storeapp.lego.utils.ResponseListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LegoRepository @Inject constructor(
    private val allProductsLegoService: AllProductsLegoService,
    private val firebaseAuth: FirebaseAuth,
    private val legoDao: LegoDao
) {
    val legos: Flow<List<LegoModel>> = legoDao.getLegos().map {
        it.map { item -> item.toModel() }
    }
    suspend fun addCart(items: List<LegoModel>) {
        withContext(Dispatchers.IO){
            if (legoDao.getCountItems() == 0) {
                legoDao.addLegos(items.map { it.toEntity() })
            }
        }
    }
    suspend fun getAll(): ResponseListState<LegoModel> {
        return withContext(Dispatchers.IO){
            val count = legoDao.getCountItems()
            when {
                count > 0 -> ResponseListState.Success(emptyList())
                else -> allProductsLegoService.getAll()
            }
        }
    }
    fun logout() = firebaseAuth.signOut()

    suspend fun deleteAll() {
        withContext(Dispatchers.IO){
            legoDao.deleteAll()
        }
    }
}