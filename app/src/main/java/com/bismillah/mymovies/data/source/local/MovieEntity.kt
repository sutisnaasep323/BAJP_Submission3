package com.bismillah.mymovies.data.source.local

data class MovieEntity(
    val overview: String,
    val originalLanguage: String,
    val releaseDate: String,
    val popularity: Double,
    val voteAverage: Double,
    val id: Int,
    val title: String,
    val posterPath: String
)