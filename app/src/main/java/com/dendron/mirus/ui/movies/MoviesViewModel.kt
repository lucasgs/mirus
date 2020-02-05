package com.dendron.mirus.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendron.mirus.repository.MovieRepository
import com.dendron.mirus.api.ApiFactory
import kotlinx.coroutines.launch
import com.dendron.mirus.api.response.Result
import kotlinx.coroutines.Dispatchers

class MoviesViewModel(
    private val movieRepository: MovieRepository = MovieRepository(ApiFactory.moviesApi)
) : ViewModel() {

    private val _movies = MutableLiveData<MoviesState>().apply {
        emptyList<Result>()
    }

    val movies: LiveData<MoviesState> = _movies

    init {
        emitDiscoverState()
    }

    fun searchMovies(query: String) {
        if (query.length > 2) {
            emitSearchState(query)
        } else if (query.isEmpty()) {
            emitDiscoverState()
        }
    }

    private fun emitDiscoverState() {
        viewModelScope.launch(Dispatchers.IO) {
            _movies.postValue(MoviesState(TITLE_DISCOVER, movieRepository.getDiscoverMovies()))
        }
    }

    private fun emitSearchState(query: String) {
        viewModelScope.launch(Dispatchers.IO){
            _movies.postValue(MoviesState(TITLE_SEARCH, movieRepository.searchMovies(query)))
        }
    }

    companion object {
        private const val TITLE_DISCOVER = "Discover"
        private const val TITLE_SEARCH = "Search"
    }

}