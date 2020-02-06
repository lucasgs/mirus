package com.dendron.mirus.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendron.mirus.repository.MovieRepository
import com.dendron.mirus.api.response.Result
import com.dendron.mirus.di.providesMoviesRepository
import com.dendron.mirus.model.Movie
import com.dendron.mirus.ui.details.toUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository = providesMoviesRepository()
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

    fun isFavoriteMovie(movie: Movie): Boolean {
        return movieRepository.isFavoriteMovie(movie)
    }

    private fun emitDiscoverState() {
        viewModelScope.launch(Dispatchers.IO) {
            _movies.postValue(
                MoviesState(
                    TITLE_DISCOVER,
                    movieRepository.getDiscoverMovies().map {
                        it.toUiModel(
                            movieRepository.isFavoriteMovie(
                                it
                            )
                        )
                    })
            )
        }
    }

    private fun emitSearchState(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _movies.postValue(
                MoviesState(
                    TITLE_SEARCH,
                    movieRepository.searchMovies(query).map {
                        it.toUiModel(
                            movieRepository.isFavoriteMovie(
                                it
                            )
                        )
                    })
            )
        }
    }

    fun toggleFavoritesMovies(showFavorites: Boolean) {
        if (showFavorites) {
            emitDiscoverState()
        } else {
            emitFavoritesState()
        }
    }

    private fun emitFavoritesState() {
        viewModelScope.launch(Dispatchers.IO) {
            _movies.postValue(
                MoviesState(
                    TITLE_FAVORITES,
                    movieRepository.getFavoritesMovie().map { it.toUiModel(true) },
                    true
                )
            )
        }
    }

    companion object {
        private const val TITLE_DISCOVER = "Discover"
        private const val TITLE_SEARCH = "Search"
        private const val TITLE_FAVORITES = "Favorites"
    }

}