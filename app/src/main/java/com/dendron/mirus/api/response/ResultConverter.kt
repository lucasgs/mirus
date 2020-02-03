package com.dendron.mirus.api.response

import com.dendron.mirus.api.ApiFactory
import com.dendron.mirus.model.Movie

fun Result.toMovie(): Movie {
    return Movie(
        id = id,
        overview = overview,
        popularity = popularity,
        voteAverage = voteAverage,
        posterPath = "${ApiFactory.TMDB_IMAGE_BASE_URL}$posterPath",
        releaseDate = releaseDate,
        title = title,
        backDropPath = "${ApiFactory.TMDB_BACLDROP_IMAGE_BASE_URL}$backdropPath"
    )
}