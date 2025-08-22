package com.sergio.sergiodevhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.sergiodevhub.core.util.Resource
import com.sergio.sergiodevhub.domain.model.Movie
import com.sergio.sergiodevhub.domain.model.Video
import com.sergio.sergiodevhub.domain.usecase.GetPopularMoviesUseCase
import com.sergio.sergiodevhub.domain.usecase.GetMovieVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            // Search through all cached pages to find the movie
            try {
                var foundMovie: Movie? = null
                var currentPage = 1
                
                // Search through multiple pages until we find the movie or run out of pages
                while (foundMovie == null && currentPage <= 10) { // Limit to 10 pages to avoid infinite loop
                    when (val result = getPopularMoviesUseCase.getPage(currentPage)) {
                        is Resource.Success -> {
                            foundMovie = result.data.movies.find { it.id == movieId }
                            if (foundMovie == null && result.data.movies.isEmpty()) {
                                // No more pages to search
                                break
                            }
                            currentPage++
                        }
                        is Resource.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = result.message ?: "Unknown error occurred"
                            )
                            return@launch
                        }
                        else -> {
                            break
                        }
                    }
                }
                
                if (foundMovie != null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        movie = foundMovie,
                        error = null
                    )
                    
                    // Load trailers after successfully loading movie
                    loadMovieTrailers(movieId)
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Movie not found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Unknown error occurred"
                )
            }
        }
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