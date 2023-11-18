package com.ogzkesk.filmapp.util

sealed interface Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error(val exception: Throwable? = null) : Resource<Nothing>
    data object Loading : Resource<Nothing>
}