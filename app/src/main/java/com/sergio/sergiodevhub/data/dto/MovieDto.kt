package com.sergio.sergiodevhub.data.dto

import com.google.gson.annotations.SerializedName
import com.sergio.sergiodevhub.domain.model.Movie

data class MovieDto(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    val popularity: Double,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val adult: Boolean,
    @SerializedName("genre_ids") val genreIds: List<Int>
)

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        backdropUrl = backdropPath?.let { "https://image.tmdb.org/t/p/w1280$it" } ?: "",
        releaseDate = releaseDate ?: "",
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity,
        originalLanguage = originalLanguage,
        adult = adult,
        genreIds = genreIds
    )
}

