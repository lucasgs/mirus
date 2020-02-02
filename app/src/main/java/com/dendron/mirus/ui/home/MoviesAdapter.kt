package com.dendron.mirus.ui.home

import com.dendron.mirus.model.Movie
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class MoviesAdapter(itemClickedListener: (Movie) -> Unit) : ListDelegationAdapter<List<Movie>>(
    moviesAdapterDelegate{ itemClickedListener.invoke(it)}
) {

    fun updateItems(movies: List<Movie>) {
        items = movies
        notifyDataSetChanged()
    }

}