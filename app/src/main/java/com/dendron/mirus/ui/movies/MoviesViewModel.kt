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

    private val _state = MutableLiveData<MoviesState>().apply {
        emptyList<Result>()
    }

    val state: LiveData<MoviesState> = _state

    private var discoverMoviesPage = 1

    init {
        emitDiscoverState()
    }

    fun getMoreMovies() {
        when(_state.value?.section ?: Section.Discover){
            Section.Discover -> {
                discoverMoviesPage++
                emitDiscoverMoreMoviesState()
            }
        }

    }

    fun searchMovies(query: String) {
        if (query.length > SEARCH_MIN_CHAR_COUNT) {
            emitSearchState(query)
        } else if (query.isEmpty()) {
            emitDiscoverState()
        }
    }

    fun isFavoriteMovie(movie: Movie): Boolean {
        return movieRepository.isFavoriteMovie(movie)
    }

    fun toggleFavoritesMovies(showFavorites: Boolean) {
        if (showFavorites) {
            discoverMoviesPage = 1
            emitDiscoverState()
        } else {
            emitFavoritesState()
        }
    }

    private fun emitDiscoverMoreMoviesState() {
        viewModelScope.launch(Dispatchers.IO) {

            val movies = _state.value?.movies ?: emptyList()

            val moreMovies = movieRepository.getDiscoverMovies(discoverMoviesPage).map {
                it.toUiModel(
                    movieRepository.isFavoriteMovie(
                        it
                    )
                )
            }

            _state.postValue(
                MoviesState(
                    section = Section.Discover,
                    movies = movies + moreMovies
                )
            )
        }
    }

    private fun emitDiscoverState() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(
                MoviesState(
                    section = Section.Discover,
                    movies = movieRepository.getDiscoverMovies(discoverMoviesPage).map {
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
            _state.postValue(
                MoviesState(
                    section = Section.Search,
                    movies = movieRepository.searchMovies(query).map {
                        it.toUiModel(
                            movieRepository.isFavoriteMovie(
                                it
                            )
                        )
                    })
            )
        }
    }

    private fun emitFavoritesState() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(
                MoviesState(
                    section = Section.Favorites,
                    movies = movieRepository.getFavoritesMovie().map { it.toUiModel(true) },
                    showFavorites = true
                )
            )
        }
    }

    companion object {
        private const val SEARCH_MIN_CHAR_COUNT = 2
    }

}