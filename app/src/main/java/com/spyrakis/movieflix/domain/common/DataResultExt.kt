@file:JvmName("DataResultExtensions")

package com.spyrakis.movieflix.domain.common

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException


inline fun <T> DataResult<T>.onAny(crossinline block: () -> Unit): DataResult<T> = run {
    block()
    this
}

inline fun <T> DataResult<T>.onSuccess(crossinline block: (T?) -> Unit): DataResult<T> =
    when (this) {
        is DataResult.Success -> apply { block(data) }
        is DataResult.Error -> this
    }

inline fun <T> DataResult<T>.onError(crossinline block: DataResult.Error.() -> Unit): DataResult<T> =
    when (this) {
        is DataResult.Error -> apply { this.block() }
        is DataResult.Success -> this
    }

suspend inline fun <T> DataResult<T>.onSuccessSuspend(crossinline block: suspend (T?) -> Unit): DataResult<T> =
    when (this) {
        is DataResult.Success -> apply { block(data) }
        is DataResult.Error -> this
    }

suspend inline fun <T> DataResult<T>.onErrorSuspend(crossinline block: suspend DataResult.Error.() -> Unit): DataResult<T> =
    when (this) {
        is DataResult.Error -> apply { this.block() }
        is DataResult.Success -> this
    }


inline fun <T, R> DataResult<T>.mapSuccess(crossinline successMapper: T.() -> R): DataResult<R> =
    when (this) {
        is DataResult.Success -> data?.successMapper().run { DataResult.Success(this) }
        is DataResult.Error -> this
    }

inline fun <T> DataResult<T>.mapError(crossinline errorMapper: () -> DataResult.Error): DataResult<T> =
    when (this) {
        is DataResult.Success -> this
        is DataResult.Error -> errorMapper()
    }

fun <T> DataResult<T>.asSuccess() = this as? DataResult.Success
fun <T> DataResult<T>.asError() = this as? DataResult.Error
fun <T> T?.asDataResult() = DataResult.Success(this)


//do not use them necessarily, added the for knowledge
/**
 * Better management of exceptions when using coroutines.
 *
 * Based on these articles:
 * https://proandroiddev.com/resilient-use-cases-with-kotlin-result-coroutines-and-annotations-511df10e2e16
 * See https://github.com/Kotlin/kotlinx.coroutines/issues/1814.
 */
inline fun <T> resultOf(block: () -> T): DataResult<T> {
    return try {
        DataResult.Success(block())
    } catch (t: TimeoutCancellationException) {
        DataResult.Error.GeneralError(t.message)
    } catch (c: CancellationException) {
        throw c
    } catch (e: Exception) {
        DataResult.Error.GeneralError(e.message)
    }
}


//when catching an exception while executing a coroutine, we don't want to swallow it
//using a simple scope without (SUpervisor job) cancels all the other coroutines running in this scope

fun <T> Flow<T>.asDataResult(): Flow<DataResult<T>> =
    this.map {
        resultOf { it }
    }.catch {
        Timber.e("asDataResult: don't try to find your error, i caught it", it)
        if (it !is CancellationException) {
            emit(DataResult.Error.GeneralError(it.message))
        } else {
            throw it
        }
    }
