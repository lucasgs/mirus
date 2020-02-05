package com.dendron.mirus.config

import com.dendron.mirus.BuildConfig

class AppConstants {
    companion object {
        const val tmdbApiKey: String = BuildConfig.TMDB_API_KEY
        const val preferencesName: String = "preferences"
        const val favoritesKey: String = "saved_favorites"
    }
}

