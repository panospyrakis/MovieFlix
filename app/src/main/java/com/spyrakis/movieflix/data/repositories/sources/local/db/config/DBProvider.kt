package com.spyrakis.movieflix.data.repositories.sources.local.db.config

import android.content.Context
import androidx.room.Room

internal object DBProvider {

    private const val DATABASE = "APP_DATABASE"
    private fun getDatabase(context: Context): MovieAppDatabase = Room.databaseBuilder(
        context,
        MovieAppDatabase::class.java,
        DATABASE
    ).build()

    internal fun getDao(context: Context) = getDatabase(context).getMovieDao()
}