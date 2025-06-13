package com.sergio.sergiodevhub.data.repository

import com.sergio.sergiodevhub.data.network.RetrofitInstance
import com.sergio.sergiodevhub.data.dto.toDomain
import com.sergio.sergiodevhub.domain.model.Movie
import com.sergio.sergiodevhub.domain.repository.MovieRepository

class MovieRepositoryImpl : MovieRepository {
    override suspend fun getPopularMovies(): List<Movie> {
        val response = RetrofitInstance.api.getPopularMovies()
        return response.results.map { it.toDomain() }
    }
}



