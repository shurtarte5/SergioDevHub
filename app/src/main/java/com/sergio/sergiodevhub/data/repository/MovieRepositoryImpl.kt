package com.sergio.sergiodevhub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sergio.sergiodevhub.data.dto.toDomain
import com.sergio.sergiodevhub.data.network.TmdbApiService
import com.sergio.sergiodevhub.data.paging.MoviePagingSource
import com.sergio.sergiodevhub.domain.model.Movie
import com.sergio.sergiodevhub.domain.model.Video
import com.sergio.sergiodevhub.domain.repository.MovieRepository
import com.sergio.sergiodevhub.domain.repository.MoviePage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApiService
) : MovieRepository {

    override suspend fun getPopularMovies(): List<Movie> {
        val response = api.getPopularMovies()
        return response.results.map { it.toDomain() }
    }

    override suspend fun getPopularMovies(page: Int): MoviePage {
        val response = api.getPopularMovies(page)
        return MoviePage(
            movies = response.results.map { it.toDomain() },
            currentPage = response.page,
            totalPages = response.total_pages,
            hasNextPage = response.page < response.total_pages
        )
    }

    override fun getPopularMoviesPaged(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { MoviePagingSource(api) }
        ).flow
    }
    
    override suspend fun getMovieVideos(movieId: Int): List<Video> {
        val response = api.getMovieVideos(movieId)
        return response.results.map { it.toDomain() }
    }
}




