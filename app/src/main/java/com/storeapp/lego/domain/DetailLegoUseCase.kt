package com.storeapp.lego.domain

import com.storeapp.lego.data.repository.DetailLegoRepository
import com.storeapp.lego.domain.model.DetailLegoModel
import com.storeapp.lego.domain.model.LegoModel
import com.storeapp.lego.utils.ResState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailLegoUseCase @Inject constructor(private val detailLegoRepository: DetailLegoRepository) {
    suspend fun getDetail(id: String): ResState<DetailLegoModel> {
        return detailLegoRepository.getDetail(id)
    }
    fun findLego(uid: String): Flow<LegoModel> {
        return detailLegoRepository.findLego(uid)
    }
}