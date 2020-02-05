package com.dendron.mirus.ui.details

import com.dendron.mirus.model.Movie

fun Movie.toUiModel(isFavorite: Boolean): MovieUIModel {
    return MovieUIModel(
        this, isFavorite
    )
}