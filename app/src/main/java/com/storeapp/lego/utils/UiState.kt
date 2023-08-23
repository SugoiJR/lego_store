package com.storeapp.lego.utils

sealed interface UIState {
    data object Loading : UIState
    data class Error(val title: String, val message: String? = "") : UIState
    data class Success<out T : Any>(val data: T) : UIState
}