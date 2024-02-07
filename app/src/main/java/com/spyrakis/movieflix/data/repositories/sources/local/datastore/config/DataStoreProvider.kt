package com.spyrakis.movieflix.data.repositories.sources.local.datastore.config

import android.content.Context

internal object DataStoreProvider {
    fun getDataStore(context: Context): DataStore = DataStoreImpl(context)
}