package com.dendron.mirus

import com.dendron.mirus.api.MoviesApi
import com.dendron.mirus.api.Result

class MovieRepository(private val api: MoviesApi) : BaseRepository() {

    suspend fun getDiscoverMovies(): List<Result> {
        return safeApiCall(
            call = { api.getPopularMoviesAsync().await() },
            error = "Error fetching news"
        )?.results!!
    }

}