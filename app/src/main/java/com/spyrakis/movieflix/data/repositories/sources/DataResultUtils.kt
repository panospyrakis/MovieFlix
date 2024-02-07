package com.spyrakis.movieflix.data.repositories.sources

import com.spyrakis.movieflix.domain.common.asDataResult
import com.spyrakis.movieflix.domain.common.DataResult
import retrofit2.Response


inline fun <T> apiCallAsDataResult(block: () -> Response<T>): DataResult<T> {

    return try {
        val response = block()
        if (response.isSuccessful) response.body().asDataResult()
        else DataResult.Error.ApiError(response.message())
    } catch (e: Exception) {
        DataResult.Error.ApiError(e.message)
    }

}