package com.spyrakis.movieflix.data.repositories.sources.local.datastore

import com.spyrakis.movieflix.data.repositories.sources.local.datastore.config.DataStore
import kotlinx.coroutines.flow.Flow

internal class PrefSourceImpl(private val dataStore: DataStore) : PrefSource {

    override val getPageCounter: Flow<Int> =
        dataStore.getPageCounter


    override suspend fun incrementPageCounter() = dataStore.incrementPageCounter()

    override suspend fun resetPage() = dataStore.resetPage()

}