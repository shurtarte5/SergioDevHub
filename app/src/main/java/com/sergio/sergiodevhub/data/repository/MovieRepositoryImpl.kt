package com.sergio.sergiodevhub.data.repository

import com.sergio.sergiodevhub.data.dto.toDomain
import com.sergio.sergiodevhub.data.network.TmdbApiService
import com.sergio.sergiodevhub.domain.model.Movie
import com.sergio.sergiodevhub.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApiService
) : MovieRepository {

    override suspend fun getPopularMovies(): List<Movie> {
        val response = api.getPopularMovies()
        return response.results.map { it.toDomain() }
    }
}