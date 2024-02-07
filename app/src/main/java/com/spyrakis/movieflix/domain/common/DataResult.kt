package com.spyrakis.movieflix.domain.common

sealed interface DataResult<out T> {
    
    class Success<T>(val data: T?): DataResult<T>
    
    sealed interface Error: DataResult<Nothing> {
        data class GeneralError(val message: String? = null): Error
        data class DatabaseError(val message: String? = null): Error
        data class ApiError(val message: String? = null): Error
        data class DatastoreError(val message: String? = null): Error
    }
    
}