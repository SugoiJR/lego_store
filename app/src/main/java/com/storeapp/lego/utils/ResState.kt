package com.storeapp.lego.utils


sealed class ResState<out T> {
    data class Error(val message: String, val cause: Exception? = null) : ResState<Nothing>()
    data class Success<out T: Any>(val data: T) : ResState<T>()
}
