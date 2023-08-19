package com.storeapp.lego.utils

import com.storeapp.lego.domain.model.LegoModel

sealed interface UiLisState {
    data object Loading : UiLisState
    data class Error(val title: String, val message: Throwable) : UiLisState
    data class Success(val data: List<LegoModel>) : UiLisState
}