package com.dendron.mirus.model

data class Movie(
    val id: Int,
    val overview: String,
    val popularity: Double,
    val posterPath: Any,
    val releaseDate: String,
    val title: String
)