package com.sergio.sergiodevhub.data.dto

import com.google.gson.annotations.SerializedName
import com.sergio.sergiodevhub.domain.model.Video

data class VideoResponseDto(
    val results: List<VideoDto>
)

data class VideoDto(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
    val official: Boolean,
    @SerializedName("published_at") val publishedAt: String?
)

fun VideoDto.toDomain(): Video {
    return Video(
        id = id,
        key = key,
        name = name,
        site = site,
        type = type,
        official = official,
        publishedAt = publishedAt ?: "",
        youtubeUrl = if (site == "YouTube") "https://www.youtube.com/watch?v=$key" else "",
        thumbnailUrl = if (site == "YouTube") "https://img.youtube.com/vi/$key/maxresdefault.jpg" else ""
    )
}