package com.spyrakis.movieflix.features.movielist

import com.spyrakis.movieflix.core.navcontroller.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeFragmentModule {
    @Provides
    @ViewModelScoped
    fun bindAppNavigator(appNavigator: Navigator) = appNavigator.asMovieListRouter()
}