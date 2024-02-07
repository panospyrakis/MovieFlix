package com.spyrakis.movieflix.data.di

import android.content.Context
import com.spyrakis.movieflix.data.repositories.MovieRepositoryImpl
import com.spyrakis.movieflix.data.repositories.sources.DataSourcesProvider
import com.spyrakis.movieflix.domain.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun bindMoviesRepository(@ApplicationContext context: Context): MovieRepository =
        MovieRepositoryImpl.createMovieRepository(
            DataSourcesProvider.getApiSource(),
            DataSourcesProvider.getDbSource(context),
            DataSourcesProvider.getPrefSource(context)
        )
}
