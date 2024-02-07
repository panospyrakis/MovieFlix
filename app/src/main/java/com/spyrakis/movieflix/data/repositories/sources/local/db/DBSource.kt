package com.spyrakis.movieflix.data.repositories.sources.local.db

import com.spyrakis.movieflix.data.repositories.sources.local.db.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

interface DBSource {
    suspend fun storeMovies(movies: List<MovieEntity>)
    fun getMovies(): Flow<List<MovieEntity>>
    fun isFavourite(id: Int): Flow<Boolean>
    suspend fun setIsFavourite(id: Int, isFavourite: Boolean)
    suspend fun deleteAllMovies()
}