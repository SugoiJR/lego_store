package com.storeapp.lego.data.repository

import com.storeapp.lego.data.dao.LegoDao
import com.storeapp.lego.data.entity.toModel
import com.storeapp.lego.data.network.services.DetailLegoService
import com.storeapp.lego.domain.model.DetailLegoModel
import com.storeapp.lego.domain.model.LegoModel
import com.storeapp.lego.utils.ResState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DetailLegoRepository @Inject constructor(
    private val detailLegoService: DetailLegoService,
    private val legoDao: LegoDao
) {
    suspend fun getDetail(id: String): ResState<DetailLegoModel> {
        return detailLegoService.getDetail(id)
    }

    fun findLego(uid: String): Flow<LegoModel> = legoDao.findLego(uid).map { it.toModel() }
}