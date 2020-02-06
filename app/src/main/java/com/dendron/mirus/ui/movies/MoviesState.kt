package com.dendron.mirus.ui.movies

import com.dendron.mirus.ui.details.MovieUIModel

class MoviesState(
    val sectionName: String,
    val movies: List<MovieUIModel>,
    val showFavorites: Boolean = false
)