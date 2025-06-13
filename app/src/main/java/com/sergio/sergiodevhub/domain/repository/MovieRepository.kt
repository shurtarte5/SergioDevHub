package com.sergio.sergiodevhub.domain.repository

import com.sergio.sergiodevhub.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
}
