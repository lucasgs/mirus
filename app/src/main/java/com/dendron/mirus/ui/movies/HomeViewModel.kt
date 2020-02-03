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

class HomeViewModel(
    private val movieRepository: MovieRepository = MovieRepository(ApiFactory.moviesApi)
) : ViewModel() {

    private val _movies = MutableLiveData<MoviesState>().apply {
        emptyList<Result>()
    }

    val movies: LiveData<MoviesState> = _movies

    init {
        GlobalScope.launch(Dispatchers.Main) {
           emitDiscoverState()
        }
    }

    fun searchMovies(query: String) {
        GlobalScope.launch(Dispatchers.Main) {

            if (query.length > 2) {
                emitSearchState(query)
            } else if(query.isEmpty()) {
                emitDiscoverState()
            }
        }
    }

    private fun emitDiscoverState() {
        GlobalScope.launch(Dispatchers.Main) {
            _movies.value = MoviesState(TITLE_DISCOVER, movieRepository.getDiscoverMovies())
        }
    }

    private fun emitSearchState(query: String) {
        GlobalScope.launch(Dispatchers.Main) {
            _movies.value = MoviesState(TITLE_SEARCH, movieRepository.searchMovies(query))
        }
    }

    companion object {
        private const val TITLE_DISCOVER = "Discover"
        private const val TITLE_SEARCH = "Search"
    }

}