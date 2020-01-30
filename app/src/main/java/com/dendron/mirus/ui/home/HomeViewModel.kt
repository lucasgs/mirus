package com.dendron.mirus.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dendron.mirus.MovieRepository
import com.dendron.mirus.api.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.dendron.mirus.api.Result

class HomeViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Result>>().apply {
        emptyList<Result>()
    }
    val movies: LiveData<List<Result>> = _movies

    init {
        GlobalScope.launch(Dispatchers.Main) {
            val movieRepository = MovieRepository(ApiFactory.moviesApi)
            val movies = movieRepository.getDiscoverMovies()

            _movies.value = movies

        }
    }


}