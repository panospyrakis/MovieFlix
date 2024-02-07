package com.spyrakis.movieflix.domain.repositories

import com.spyrakis.movieflix.domain.common.DataResult
import com.spyrakis.movieflix.domain.models.Movie
import com.spyrakis.movieflix.domain.models.MovieDetails
import com.spyrakis.movieflix.domain.models.Review
import com.spyrakis.movieflix.domain.models.SimilarMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMoviesForList(): Flow<List<Movie>>

    suspend fun setIsFavourite(id: Int, isFavourite: Boolean)

    suspend fun fetchNextMoviePage(): DataResult<Any>

    suspend fun deleteAll()

    suspend fun getMovieDetails(id: Int): DataResult<MovieDetails>

    fun isFavourite(id: Int): Flow<Boolean>

    suspend fun getSimilarMovies(id: Int): DataResult<List<SimilarMovie>>

    suspend fun getReviews(id: Int): DataResult<List<Review>>
}