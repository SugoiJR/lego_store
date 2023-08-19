package com.storeapp.lego.utils

sealed class ResponseListState<out T> {
    data class Error(val message: String, val cause: Exception? = null) : ResponseListState<Nothing>()
    data class Success<out T: Any>(val data: List<T>) : ResponseListState<T>()
}