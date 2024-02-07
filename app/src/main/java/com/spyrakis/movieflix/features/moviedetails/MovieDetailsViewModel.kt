package com.spyrakis.movieflix.features.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spyrakis.movieflix.domain.common.onErrorSuspend
import com.spyrakis.movieflix.domain.common.onSuccessSuspend
import com.spyrakis.movieflix.domain.usecases.GetMovieDetailsUseCase
import com.spyrakis.movieflix.domain.usecases.GetReviewsUseCase
import com.spyrakis.movieflix.domain.usecases.GetSimilarMoviesUseCase
import com.spyrakis.movieflix.domain.usecases.IsFavouriteUseCase
import com.spyrakis.movieflix.domain.usecases.SetIsFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val isFavouriteUseCase: IsFavouriteUseCase,
    private val setIsFavouriteUseCase: SetIsFavouriteUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getReviewsUseCase: GetReviewsUseCase
) : ViewModel() {
    private var _uiStateFlow = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Initial)
    val uiStateFlow: StateFlow<MovieDetailsState>
        get() = _uiStateFlow
    private var id: Int = -1
    private var isFavourite: Boolean = false

    fun setId(id: Int) {
        this.id = id
        viewModelScope.launch {
            getMovieDetailsUseCase(id).onErrorSuspend {
                _uiStateFlow.emit(MovieDetailsState.Error.Details)
            }.onSuccessSuspend {
                it?.let {
                    _uiStateFlow.emit(MovieDetailsState.Success.Details(it))
                } ?: run {
                    _uiStateFlow.emit(MovieDetailsState.Error.Details)
                }
            }
        }

        viewModelScope.launch {
            isFavouriteUseCase(id).collect {
                isFavourite = it
                _uiStateFlow.emit(MovieDetailsState.Success.Favourite(it))
            }
        }

        viewModelScope.launch {
            getSimilarMoviesUseCase(id).onSuccessSuspend {
                it?.let {
                    _uiStateFlow.emit(MovieDetailsState.Success.Similar(it))
                } ?: run {
                    _uiStateFlow.emit(MovieDetailsState.Error.Similar)
                }
            }.onErrorSuspend {
                _uiStateFlow.emit(MovieDetailsState.Error.Similar)
            }
        }

        viewModelScope.launch {
            getReviewsUseCase(id).onSuccessSuspend {
                it?.let {
                    _uiStateFlow.emit(MovieDetailsState.Success.Reviews(it))
                } ?: run {
                    _uiStateFlow.emit(MovieDetailsState.Error.Reviews)
                }
            }.onErrorSuspend {
                _uiStateFlow.emit(MovieDetailsState.Error.Reviews)
            }
        }
    }


fun markFavourite() = viewModelScope.launch { setIsFavouriteUseCase(id, isFavourite) }
}