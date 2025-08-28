package com.sergio.sergiodevhub.domain.repository

import androidx.paging.PagingData
import com.sergio.sergiodevhub.domain.model.Movie
import com.sergio.sergiodevhub.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMoviesPaged(): Flow<PagingData<Movie>>
    suspend fun getMovieVideos(movieId: Int): List<Video>
}
