package com.dendron.mirus.ui.movies

import com.dendron.mirus.ui.details.MovieUIModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class MoviesAdapter(itemClickedListener: (MovieUIModel) -> Unit) : ListDelegationAdapter<List<MovieUIModel>>(
    moviesAdapterDelegate {
        itemClickedListener.invoke(
            it
        )
    }
) {

    fun updateItems(movies: List<MovieUIModel>) {
        items = movies
        notifyDataSetChanged()
    }

}