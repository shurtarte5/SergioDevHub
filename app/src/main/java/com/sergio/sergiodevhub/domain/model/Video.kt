package com.sergio.sergiodevhub.domain.model

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
    val official: Boolean,
    val publishedAt: String,
    val youtubeUrl: String,
    val thumbnailUrl: String
) {
    val isTrailer: Boolean get() = type == "Trailer"
    val isTeaser: Boolean get() = type == "Teaser"
    val isYouTube: Boolean get() = site == "YouTube"
    val isOfficial: Boolean get() = official
}