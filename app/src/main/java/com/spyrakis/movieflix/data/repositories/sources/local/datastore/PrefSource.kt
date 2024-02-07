package com.spyrakis.movieflix.data.repositories.sources.local.datastore

import kotlinx.coroutines.flow.Flow

interface PrefSource {
    val getPageCounter: Flow<Int>
    suspend fun incrementPageCounter()
    suspend fun resetPage()
}