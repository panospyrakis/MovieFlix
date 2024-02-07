package com.spyrakis.movieflix.data.repositories.sources.remote.config

import com.spyrakis.movieflix.data.repositories.sources.remote.dto.MovieDetailsDto
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.MovieListDto
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.ReviewsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("3/movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int? = 0,
        @Query("language") language: String = "en-US"
    ): Response<MovieListDto>

    @GET("3/movie/{id}/reviews")
    suspend fun getReviews(
        @Path("id") id: Int,
        @Query("page") page: Int? = 1,
        @Query("language") language: String = "en-US",
    ): Response<ReviewsDto>

    @GET("3/movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") id: Int,
        @Query("page") page: Int? = 1,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sort: String = "vote_average.desc"
    ): Response<MovieListDto>

    @GET("3/movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int, @Query("append_to_response") appendToResponse: String = "credits"
    ): Response<MovieDetailsDto>
}