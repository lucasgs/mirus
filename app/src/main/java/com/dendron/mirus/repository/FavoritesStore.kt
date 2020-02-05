package com.dendron.mirus.repository

import android.content.SharedPreferences
import com.dendron.mirus.config.AppConstants

interface FavoritesStore {
    fun saveFavoriteMovie(data: String)
    fun getFavoritesMovie(): String
}

class SimpleFavoritesStore(
    private val sharedPreferences: SharedPreferences
) : FavoritesStore {

    override fun saveFavoriteMovie(data: String) {
        with(sharedPreferences.edit()){
            putString(AppConstants.favoritesKey, data)
            commit()
        }
    }

    override fun getFavoritesMovie(): String {
        return sharedPreferences.getString(AppConstants.favoritesKey, "").toString()
    }
}