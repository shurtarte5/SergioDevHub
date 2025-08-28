package com.sergio.sergiodevhub.domain.usecase

import com.sergio.sergiodevhub.core.util.Resource
import com.sergio.sergiodevhub.domain.model.Video
import com.sergio.sergiodevhub.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Resource<List<Video>> {
        return try {
            val videos = repository.getMovieVideos(movieId)
            Resource.Success(videos)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load movie videos")
        }
    }
    
    suspend fun getTrailers(movieId: Int): Resource<List<Video>> {
        return try {
            val videos = repository.getMovieVideos(movieId)
            val trailers = videos.filter { it.isTrailer && it.isYouTube }
                .sortedByDescending { it.official }
            Resource.Success(trailers)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load movie trailers")
        }
    }
    
    suspend fun getOfficialTrailer(movieId: Int): Resource<Video?> {
        return try {
            val videos = repository.getMovieVideos(movieId)
            val officialTrailer = videos.find { 
                it.isTrailer && it.isYouTube && it.isOfficial 
            } ?: videos.find { 
                it.isTrailer && it.isYouTube 
            }
            Resource.Success(officialTrailer)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load official trailer")
        }
    }
}