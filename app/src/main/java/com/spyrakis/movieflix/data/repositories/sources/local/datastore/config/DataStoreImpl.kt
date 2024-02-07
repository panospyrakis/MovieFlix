package com.spyrakis.movieflix.data.repositories.sources.local.datastore.config

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DataStoreImpl(private val context: Context) : DataStore {
    private val Context.dataStore by preferencesDataStore(name = "settings")


    override val getPageCounter: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[PAGE_COUNTER] ?: 1
        }

    override suspend fun incrementPageCounter() {
        context.dataStore.edit { settings ->
            val currentCounterValue = settings[PAGE_COUNTER] ?: 1
            settings[PAGE_COUNTER] = currentCounterValue + 1
        }
    }

    override suspend fun resetPage() {
        context.dataStore.edit { settings ->
            settings[PAGE_COUNTER] = 1
        }
    }

    companion object {
        private val PAGE_COUNTER = intPreferencesKey("page_counter")
    }
}