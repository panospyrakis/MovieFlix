package com.spyrakis.movieflix.data.repositories.sources.local.db.config


import androidx.room.Database
import androidx.room.RoomDatabase
import com.spyrakis.movieflix.data.repositories.sources.local.db.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieAppDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}