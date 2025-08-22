package com.sergio.sergiodevhub.domain.usecase

import androidx.paging.PagingData
import com.sergio.sergiodevhub.core.util.Resource
import com.sergio.sergiodevhub.domain.model.Movie
import com.sergio.sergiodevhub.domain.repository.MovieRepository
import com.sergio.sergiodevhub.domain.repository.MoviePage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPopularMoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        try {
            val movies = repository.getPopularMovies()
            emit(Resource.Success(movies))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
    }

    suspend fun getPage(page: Int): Resource<MoviePage> {
        return try {
            val moviePage = repository.getPopularMovies(page)
            Resource.Success(moviePage)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    fun getPaged(): Flow<PagingData<Movie>> {
        return repository.getPopularMoviesPaged()
    }
}
