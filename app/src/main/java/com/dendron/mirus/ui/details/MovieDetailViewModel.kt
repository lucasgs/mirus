package com.dendron.mirus.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendron.mirus.repository.MovieRepository
import com.dendron.mirus.api.response.Result
import com.dendron.mirus.di.providesMoviesRepository
import com.dendron.mirus.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieRepository: MovieRepository = providesMoviesRepository()
) : ViewModel() {

    private val _movies = MutableLiveData<MovieDetailState>().apply {
        emptyList<Result>()
    }

    val movies: LiveData<MovieDetailState> = _movies

    fun saveFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.saveFavoriteMovie(movie)
        }
    }
}