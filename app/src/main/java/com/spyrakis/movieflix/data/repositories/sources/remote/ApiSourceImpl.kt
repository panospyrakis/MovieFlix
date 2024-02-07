package com.spyrakis.movieflix.data.repositories.sources.remote

import com.spyrakis.movieflix.data.repositories.sources.apiCallAsDataResult
import com.spyrakis.movieflix.data.repositories.sources.remote.config.MoviesApi
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.MovieDetailsDto
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.MovieListDto
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.ReviewsDto
import com.spyrakis.movieflix.domain.common.DataResult

internal class ApiSourceImpl(private val apiService: MoviesApi) :
    ApiSource {
    override suspend fun getMovies(page: Int): DataResult<MovieListDto> =
        apiCallAsDataResult {
            apiService.getMovies(page)
        }

    override suspend fun getSimilarMovies(id: Int): DataResult<MovieListDto> =
        apiCallAsDataResult {
            apiService.getSimilarMovies(id)
        }

    override suspend fun getMovieDetails(id: Int): DataResult<MovieDetailsDto> =
        apiCallAsDataResult {
            apiService.getMovieDetails(id)
        }

    override suspend fun getReviews(id: Int): DataResult<ReviewsDto>  =
        apiCallAsDataResult {
            apiService.getReviews(id)
        }
}