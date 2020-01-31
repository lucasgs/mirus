package com.dendron.mirus.repository

import com.dendron.mirus.api.MoviesApi
import com.dendron.mirus.api.response.toMovie
import com.dendron.mirus.model.Movie

class MovieRepository(private val api: MoviesApi) : BaseRepository() {

    suspend fun getDiscoverMovies(): List<Movie> {
        return safeApiCall(
            call = { api.getPopularMoviesAsync().await() },
            error = "Error fetching news"
        )?.results!!.map { it.toMovie() }
    }

}