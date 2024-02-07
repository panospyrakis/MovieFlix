package com.spyrakis.movieflix.data.repositories

import com.spyrakis.movieflix.domain.common.mapSuccess
import com.spyrakis.movieflix.domain.common.onSuccessSuspend
import com.spyrakis.movieflix.data.repositories.sources.local.datastore.PrefSource
import com.spyrakis.movieflix.data.repositories.sources.local.db.DBSource
import com.spyrakis.movieflix.data.repositories.sources.remote.ApiSource
import com.spyrakis.movieflix.domain.common.DataResult
import com.spyrakis.movieflix.domain.models.Movie
import com.spyrakis.movieflix.domain.models.MovieDetails
import com.spyrakis.movieflix.domain.models.Review
import com.spyrakis.movieflix.domain.models.SimilarMovie
import com.spyrakis.movieflix.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class MovieRepositoryImpl(
    private val apiSource: ApiSource,
    private val dbSource: DBSource,
    private val prefSource: PrefSource
) : MovieRepository {


    override fun getMoviesForList(): Flow<List<Movie>> = getLocalMovies().onEach { localList ->
        if (localList.isEmpty()) {
            Timber.d("getMoviesForList: local movies is empty")
            fetchNextMoviePage()
        }
    }


    override suspend fun setIsFavourite(id: Int, isFavourite: Boolean) =
        dbSource.setIsFavourite(id, isFavourite)

    private fun getLocalMovies() =
        dbSource.getMovies().distinctUntilChanged().map { it.entitiesToMovies() }

    private suspend fun fetchMovies(page: Int) = apiSource.getMovies(page).mapSuccess {
        this.results.dtosToMovies()
    }

    override suspend fun fetchNextMoviePage(): DataResult<Any> {
        val page = getPage()
        return fetchMovies(page).onSuccessSuspend { remoteList ->
            Timber.d("getMoviesForList: Movies fetched successfully")
            remoteList?.let {
                storeMovies(remoteList)
                incrementPageCounter()
            }
        }
    }

    override fun isFavourite(id: Int): Flow<Boolean> = dbSource.isFavourite(id)

    override suspend fun getSimilarMovies(id: Int): DataResult<List<SimilarMovie>> =
        apiSource.getSimilarMovies(id).mapSuccess {
            this.results.dtosToSimilarMovies()
        }

    override suspend fun getReviews(id: Int): DataResult<List<Review>> =
        apiSource.getReviews(id).mapSuccess {
            this.reviews.dtosToReviews()
        }

    override suspend fun deleteAll() {
        prefSource.resetPage()
        dbSource.deleteAllMovies()
    }

    override suspend fun getMovieDetails(id: Int): DataResult<MovieDetails> =
        apiSource.getMovieDetails(id).mapSuccess {
            this.dtoToMovieDetails()
        }

    private suspend fun incrementPageCounter() = prefSource.incrementPageCounter()

    private suspend fun getPage() = prefSource.getPageCounter.first()

    private suspend fun storeMovies(movies: List<Movie>) =
        dbSource.storeMovies(movies.moviesToEntities())

    companion object {
        internal fun createMovieRepository(
            apiSource: ApiSource, dbSource: DBSource, prefSource: PrefSource
        ): MovieRepository {
            return MovieRepositoryImpl(
                apiSource, dbSource, prefSource
            )
        }
    }
}