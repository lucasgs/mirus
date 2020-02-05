package com.dendron.mirus.di

import android.content.Context
import com.dendron.mirus.MoviesApplication
import com.dendron.mirus.api.ApiFactory
import com.dendron.mirus.config.AppConstants
import com.dendron.mirus.repository.MovieRepository
import com.dendron.mirus.repository.SimpleFavoritesStore

fun providesMoviesRepository() = MovieRepository(
    ApiFactory.moviesApi,
    SimpleFavoritesStore(
        MoviesApplication.applicationContext().getSharedPreferences(
            AppConstants.preferencesName,
            Context.MODE_PRIVATE
        )
    )
)