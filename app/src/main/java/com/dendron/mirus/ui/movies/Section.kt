package com.dendron.mirus.ui.movies

sealed class Section(val name: String) {
    object Discover : Section(TITLE_DISCOVER)
    object Search : Section(TITLE_SEARCH)
    object Favorites : Section(TITLE_FAVORITES)

    companion object {
        private const val TITLE_DISCOVER = "Discover"
        private const val TITLE_SEARCH = "Search"
        private const val TITLE_FAVORITES = "Favorites"
    }
}