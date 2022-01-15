package com.stustirling.clearscore.core.ui

sealed class ViewState<out T> {
    object Loading: ViewState<Nothing>()
    data class Error(val error: Throwable? = null) : ViewState<Nothing>()
    data class Success<T>(val item: T) : ViewState<T>()
}