package com.dendron.mirus.repository

import com.dendron.mirus.api.MoviesApi
import com.dendron.mirus.api.response.toMovie
import com.dendron.mirus.model.Movie

class MovieRepository(private val api: MoviesApi) : BaseRepository() {

    suspend fun getDiscoverMovies(): List<Movie> {
        return safeApiCall(
            call = { api.getPopularMoviesAsync().await() },
            error = "Error fetching movies"
        )?.results?.map { it.toMovie() } ?: emptyList()
    }

    suspend fun searchMovies(query: String): List<Movie> {
        return safeApiCall(
            call = { api.searchMoviesAsync(query).await() },
            error = "Error searching movies"
        )?.results?.map { it.toMovie() } ?: emptyList()
    }

}