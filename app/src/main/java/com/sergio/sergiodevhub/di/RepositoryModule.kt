package com.sergio.sergiodevhub.di

import com.sergio.sergiodevhub.data.network.TmdbApiService
import com.sergio.sergiodevhub.data.repository.MovieRepositoryImpl
import com.sergio.sergiodevhub.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: TmdbApiService
    ): MovieRepository = MovieRepositoryImpl(api)
}
