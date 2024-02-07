package com.spyrakis.movieflix.domain.usecases

import com.spyrakis.movieflix.domain.common.asDataResult
import com.spyrakis.movieflix.domain.common.DataResult
import com.spyrakis.movieflix.domain.models.Movie
import com.spyrakis.movieflix.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesForListUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    operator fun invoke(): Flow<DataResult<List<Movie>>> = movieRepository.getMoviesForList().asDataResult()
}