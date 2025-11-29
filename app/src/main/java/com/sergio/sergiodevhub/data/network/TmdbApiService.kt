package com.sergio.sergiodevhub.data.network

import com.sergio.sergiodevhub.data.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): MovieResponseDto
}