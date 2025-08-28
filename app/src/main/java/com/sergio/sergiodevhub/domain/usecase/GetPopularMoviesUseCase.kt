package com.sergio.sergiodevhub.domain.usecase

import androidx.paging.PagingData
import com.sergio.sergiodevhub.domain.model.Movie
import com.sergio.sergiodevhub.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(
    private val repository: MovieRepository
) {
    fun getPaged(): Flow<PagingData<Movie>> {
        return repository.getPopularMoviesPaged()
    }
}
