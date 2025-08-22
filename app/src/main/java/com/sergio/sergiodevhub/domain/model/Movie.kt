package com.sergio.sergiodevhub.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val originalLanguage: String,
    val adult: Boolean,
    val genreIds: List<Int>
) {
    // Utility properties for display
    val formattedRating: String
        get() = String.format("%.1f", voteAverage)
    
    val releaseYear: String
        get() = if (releaseDate.isNotEmpty() && releaseDate.length >= 4) {
            releaseDate.substring(0, 4)
        } else ""
    
    val formattedReleaseDate: String
        get() = if (releaseDate.isNotEmpty()) {
            try {
                val parts = releaseDate.split("-")
                if (parts.size == 3) {
                    val months = listOf("", "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
                    "${months.getOrNull(parts[1].toInt()) ?: ""} ${parts[2]}, ${parts[0]}"
                } else releaseDate
            } catch (e: Exception) {
                releaseDate
            }
        } else "Release date unknown"
}

