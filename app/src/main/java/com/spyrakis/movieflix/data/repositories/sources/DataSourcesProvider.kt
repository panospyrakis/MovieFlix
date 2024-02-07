package com.spyrakis.movieflix.data.repositories.sources

import android.content.Context
import com.spyrakis.movieflix.data.repositories.sources.local.datastore.PrefSource
import com.spyrakis.movieflix.data.repositories.sources.local.datastore.PrefSourceImpl
import com.spyrakis.movieflix.data.repositories.sources.local.datastore.config.DataStoreProvider
import com.spyrakis.movieflix.data.repositories.sources.local.db.DBSource
import com.spyrakis.movieflix.data.repositories.sources.local.db.DBSourceImpl
import com.spyrakis.movieflix.data.repositories.sources.local.db.config.DBProvider
import com.spyrakis.movieflix.data.repositories.sources.remote.ApiSource
import com.spyrakis.movieflix.data.repositories.sources.remote.ApiSourceImpl
import com.spyrakis.movieflix.data.repositories.sources.remote.config.ApiProvider
import com.spyrakis.movieflix.data.repositories.sources.remote.config.MoviesApi

internal object DataSourcesProvider {
    fun getApiSource(): ApiSource = ApiSourceImpl(ApiProvider.getApi<MoviesApi>())
    fun getDbSource(context: Context): DBSource = DBSourceImpl(DBProvider.getDao(context))
    fun getPrefSource(context: Context): PrefSource =
        PrefSourceImpl(DataStoreProvider.getDataStore(context))
}
