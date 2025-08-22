package com.sergio.sergiodevhub.domain.repository

import androidx.paging.PagingData
import com.sergio.sergiodevhub.domain.model.Movie
import kotlinx.coroutines.flow.Flow

data class MoviePage(
    val movies: List<Movie>,
    val currentPage: Int,
    val totalPages: Int,
    val hasNextPage: Boolean
)

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getPopularMovies(page: Int): MoviePage
    fun getPopularMoviesPaged(): Flow<PagingData<Movie>>
}
