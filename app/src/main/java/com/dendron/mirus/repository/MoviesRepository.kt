package com.dendron.mirus.repository

import com.dendron.mirus.api.MoviesApi
import com.dendron.mirus.api.response.toMovie
import com.dendron.mirus.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class MovieRepository(
    private val api: MoviesApi,
    private val favoritesStore: FavoritesStore
) : BaseRepository() {

    private val gson = Gson()
    private val moviesType = object : TypeToken<List<Movie>>() {}.type

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

    fun saveFavoriteMovie(movie: Movie) {
        val movies = getFavoritesMovie().toMutableList()
        if (!movies.contains(movie)) {
            movies.add(movie)
            val data = gson.toJson(movies)
            favoritesStore.saveFavoriteMovie(data)
        }
    }

    fun removeFavoriteMovie(movie: Movie) {
        val movies = getFavoritesMovie().toMutableList()
        if (movies.contains(movie)) {
            movies.remove(movie)
            val data = gson.toJson(movies)
            favoritesStore.saveFavoriteMovie(data)
        }
    }

    fun getFavoritesMovie(): List<Movie> {
        val data = favoritesStore.getFavoritesMovie()
        if (data.isNullOrEmpty()) return emptyList()
        return gson.fromJson<List<Movie>>(data, moviesType)
    }

    fun isFavoriteMovie(movie: Movie): Boolean {
        val movies = getFavoritesMovie()
        return if (movies.contains(movie)) {
            movies.any { it.title == movie.title }
        } else {
            false
        }
    }


}