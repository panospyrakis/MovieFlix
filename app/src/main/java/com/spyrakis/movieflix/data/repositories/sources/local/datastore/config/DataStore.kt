package com.spyrakis.movieflix.data.repositories.sources.local.datastore.config

import kotlinx.coroutines.flow.Flow

interface DataStore {
    val getPageCounter: Flow<Int>
    suspend fun incrementPageCounter()
    suspend fun resetPage()
}