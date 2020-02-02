package com.dendron.mirus.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dendron.mirus.repository.MovieRepository
import com.dendron.mirus.api.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.dendron.mirus.api.response.Result
import com.dendron.mirus.model.Movie

class HomeViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>().apply {
        emptyList<Result>()
    }
    val movies: LiveData<List<Movie>> = _movies

    init {
        GlobalScope.launch(Dispatchers.Main) {
            val movieRepository =
                MovieRepository(ApiFactory.moviesApi)
            _movies.value = movieRepository.getDiscoverMovies()
        }
    }

}