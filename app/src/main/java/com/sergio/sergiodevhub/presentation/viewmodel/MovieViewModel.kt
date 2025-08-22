package com.sergio.sergiodevhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sergio.sergiodevhub.core.util.Resource
import com.sergio.sergiodevhub.domain.model.Movie
import com.sergio.sergiodevhub.domain.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String? = null,
    val currentPage: Int = 1,
    val hasNextPage: Boolean = false,
    val isLoadingMore: Boolean = false
)

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState

    val pagedMovies: Flow<PagingData<Movie>> = getPopularMoviesUseCase
        .getPaged()
        .cachedIn(viewModelScope)

    init {
        fetchMoviesWithPagination()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }

                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            movies = result.data,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message ?: "Unknown error"
                        )
                    }
                }
            }
        }
    }

    fun fetchMoviesWithPagination(page: Int = 1) {
        viewModelScope.launch {
            if (page == 1) {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            } else {
                _uiState.value = _uiState.value.copy(isLoadingMore = true)
            }

            when (val result = getPopularMoviesUseCase.getPage(page)) {
                is Resource.Success -> {
                    val currentMovies = if (page == 1) emptyList() else _uiState.value.movies
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        movies = currentMovies + result.data.movies,
                        currentPage = result.data.currentPage,
                        hasNextPage = result.data.hasNextPage,
                        error = null
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        error = result.message ?: "Unknown error"
                    )
                }
                else -> {}
            }
        }
    }

    fun loadMoreMovies() {
        if (!_uiState.value.isLoadingMore && _uiState.value.hasNextPage) {
            fetchMoviesWithPagination(_uiState.value.currentPage + 1)
        }
    }

    fun retry() {
        fetchMoviesWithPagination()
    }
}


