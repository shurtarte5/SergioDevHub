package com.sergio.sergiodevhub.di

import com.sergio.sergiodevhub.data.repository.MovieRepositoryImpl
import com.sergio.sergiodevhub.domain.repository.MovieRepository
import com.sergio.sergiodevhub.domain.usecase.GetPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRepository {
        return MovieRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(
        repository: MovieRepository
    ): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(repository)
    }
}
