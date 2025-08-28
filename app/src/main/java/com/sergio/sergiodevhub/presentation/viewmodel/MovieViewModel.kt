package com.sergio.sergiodevhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sergio.sergiodevhub.domain.model.Movie
import com.sergio.sergiodevhub.domain.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    val pagedMovies: Flow<PagingData<Movie>> = getPopularMoviesUseCase
        .getPaged()
        .cachedIn(viewModelScope)
}


