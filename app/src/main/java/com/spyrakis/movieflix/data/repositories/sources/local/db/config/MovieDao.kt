package com.spyrakis.movieflix.data.repositories.sources.local.db.config

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spyrakis.movieflix.data.repositories.sources.local.db.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE movie SET isFavourite = :isFavourite WHERE id =:id")
    suspend fun setIsFavourite(id: Int, isFavourite: Boolean)

    @Query("SELECT isFavourite FROM movie WHERE id =:id")
    fun isFavourite(id: Int): Flow<Boolean>

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovies()

}