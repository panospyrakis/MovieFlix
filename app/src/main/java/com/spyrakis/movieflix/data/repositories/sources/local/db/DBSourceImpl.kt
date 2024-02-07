package com.spyrakis.movieflix.data.repositories.sources.local.db

import com.spyrakis.movieflix.data.repositories.sources.local.db.config.MovieDao
import com.spyrakis.movieflix.data.repositories.sources.local.db.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

internal class DBSourceImpl(private val moviesDao: MovieDao) : DBSource {
    override suspend fun storeMovies(movies: List<MovieEntity>) = moviesDao.insertAll(movies)

    override fun getMovies(): Flow<List<MovieEntity>> =
        moviesDao.getMovies()

    override fun isFavourite(id: Int): Flow<Boolean> =
        moviesDao.isFavourite(id)

    override suspend fun setIsFavourite(id: Int, isFavourite: Boolean) =
        moviesDao.setIsFavourite(id, isFavourite)

    override suspend fun deleteAllMovies() {
        moviesDao.deleteAllMovies()
    }
}