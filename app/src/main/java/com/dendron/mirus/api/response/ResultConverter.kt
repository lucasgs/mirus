package com.dendron.mirus.api.response

import com.dendron.mirus.model.Movie

fun Result.toMovie(): Movie {
    return Movie(
        id = id,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title
    )
}