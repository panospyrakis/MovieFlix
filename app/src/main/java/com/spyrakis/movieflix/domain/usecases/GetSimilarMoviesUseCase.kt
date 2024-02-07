package com.spyrakis.movieflix.domain.usecases

import com.spyrakis.movieflix.domain.common.DataResult
import com.spyrakis.movieflix.domain.models.SimilarMovie
import com.spyrakis.movieflix.domain.repositories.MovieRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(id: Int): DataResult<List<SimilarMovie>> =
        movieRepository.getSimilarMovies(id)
}
