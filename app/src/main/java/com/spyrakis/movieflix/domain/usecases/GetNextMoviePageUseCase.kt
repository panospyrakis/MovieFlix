package com.spyrakis.movieflix.domain.usecases

import com.spyrakis.movieflix.domain.common.DataResult
import com.spyrakis.movieflix.domain.repositories.MovieRepository
import javax.inject.Inject

class GetNextMoviePageUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): DataResult<Any> = movieRepository.fetchNextMoviePage()
}