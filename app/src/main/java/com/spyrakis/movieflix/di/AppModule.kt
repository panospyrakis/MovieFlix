package com.spyrakis.movieflix.di

import com.spyrakis.movieflix.core.navcontroller.AppNavigator
import com.spyrakis.movieflix.core.navcontroller.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun bindAppNavigator(): Navigator = AppNavigator()
}