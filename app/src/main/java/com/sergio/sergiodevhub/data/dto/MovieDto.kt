package com.sergio.sergiodevhub.data.dto

import com.google.gson.annotations.SerializedName
import com.sergio.sergiodevhub.domain.model.Movie

data class MovieDto(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("overview") val overview: String
)

// 👇 Esta función debe estar FUERA de la clase
fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = "https://image.tmdb.org/t/p/w500$posterPath"
    )
}

