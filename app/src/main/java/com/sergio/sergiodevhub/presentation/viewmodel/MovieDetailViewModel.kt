package com.sergio.sergiodevhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.sergio.sergiodevhub.core.util.Resource
import com.sergio.sergiodevhub.domain.model.Movie
import com.sergio.sergiodevhub.domain.model.Video
import com.sergio.sergiodevhub.domain.usecase.GetPopularMoviesUseCase
import com.sergio.sergiodevhub.domain.usecase.GetMovieVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String? = null,
    val officialTrailer: Video? = null,
    val allTrailers: List<Video> = emptyList(),
    val isLoadingTrailers: Boolean = false
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState

    private val pagedMovies = getPopularMoviesUseCase.getPaged().cachedIn(viewModelScope)

    fun loadMovieDetails(movieId: Int) {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = "Please pass movie object directly using loadMovieDetailsDirectly()"
        )
    }
    
    fun loadMovieDetailsDirectly(movie: Movie) {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            movie = movie,
            error = null
        )
        loadMovieTrailers(movie.id)
    }
    
    private fun loadMovieTrailers(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingTrailers = true)
            
            // Load official trailer
            when (val trailerResult = getMovieVideosUseCase.getOfficialTrailer(movieId)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        officialTrailer = trailerResult.data
                    )
                }
                is Resource.Error -> {
                    // Don't show error for trailers, it's optional content
                }
                is Resource.Loading -> {
                    // Handle loading state if needed
                }
            }
            
            // Load all trailers
            when (val trailersResult = getMovieVideosUseCase.getTrailers(movieId)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        allTrailers = trailersResult.data,
                        isLoadingTrailers = false
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingTrailers = false
                    )
                }
                is Resource.Loading -> {
                    // Handle loading state if needed
                }
            }
        }
    }
}