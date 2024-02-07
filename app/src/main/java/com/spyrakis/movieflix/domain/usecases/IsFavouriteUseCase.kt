package com.spyrakis.movieflix.domain.usecases

import com.spyrakis.movieflix.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavouriteUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    operator fun invoke(id: Int): Flow<Boolean> =
        movieRepository.isFavourite(id)
}
