package com.spyrakis.movieflix.features.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spyrakis.movieflix.domain.common.onErrorSuspend
import com.spyrakis.movieflix.domain.common.onSuccessSuspend
import com.spyrakis.movieflix.domain.usecases.GetMoviesForListUseCase
import com.spyrakis.movieflix.domain.usecases.GetNextMoviePageUseCase
import com.spyrakis.movieflix.domain.usecases.RefreshItemsUseCase
import com.spyrakis.movieflix.domain.usecases.SetIsFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesForListUseCase: GetMoviesForListUseCase,
    private val setIsFavouriteUseCase: SetIsFavouriteUseCase,
    private val getNextMoviePageUseCase: GetNextMoviePageUseCase,
    private val refreshItemsUseCase: RefreshItemsUseCase,
    private val router: MovieListRouter
) : ViewModel() {
    private var _uiStateFlow = MutableStateFlow<MovieListState>(MovieListState.Initial)
    val uiStateFlow: StateFlow<MovieListState>
        get() = _uiStateFlow

    init {
        viewModelScope.launch {
            getMoviesForListUseCase().collect {
                it.onSuccessSuspend { list ->
                    list?.let {
                        Timber.d("get list success: fetched data size --> ${list.size}")
                        _uiStateFlow.emit(MovieListState.Success(list.map { item ->
                            MovieListItem.Movie(
                                item
                            )
                        }))
                    } ?: run {
                        Timber.d("list get error: empty data??")
                        _uiStateFlow.emit(MovieListState.Error)
                    }
                }.onErrorSuspend {
                    Timber.d("list get error: caught an error ? wtf ---> $this ")
                    _uiStateFlow.emit(MovieListState.Error)
                }
            }
        }
    }

    fun onBottomReached() = viewModelScope.launch {
        getNextMoviePageUseCase().onErrorSuspend {
            Timber.d("list fetch error: caught an error ? wtf ---> $this ")
            _uiStateFlow.emit(MovieListState.Error)
        }
    }

    fun markFavourite(id: Int, isFavourite: Boolean) =
        viewModelScope.launch { setIsFavouriteUseCase(id, isFavourite) }

    fun onItemSelected(id: Int) = router.navToDetails(id)

    fun refresh() = viewModelScope.launch {
        _uiStateFlow.emit(MovieListState.Success(listOf()))
        refreshItemsUseCase()
    }
}