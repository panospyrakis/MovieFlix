package com.spyrakis.movieflix.data.repositories.sources.remote

import com.spyrakis.movieflix.data.repositories.sources.remote.dto.MovieDetailsDto
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.MovieListDto
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.ReviewsDto
import com.spyrakis.movieflix.domain.common.DataResult

interface ApiSource {
    suspend fun getMovies(page: Int): DataResult<MovieListDto>
    suspend fun getSimilarMovies(id: Int): DataResult<MovieListDto>
    suspend fun getMovieDetails(id:Int): DataResult<MovieDetailsDto>
    suspend fun getReviews(id:Int): DataResult<ReviewsDto>
}