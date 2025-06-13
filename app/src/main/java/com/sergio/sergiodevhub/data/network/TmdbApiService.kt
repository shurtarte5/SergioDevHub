package com.sergio.sergiodevhub.data.network

import com.sergio.sergiodevhub.data.dto.MovieResponseDto

import retrofit2.http.GET



interface TmdbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponseDto
}



