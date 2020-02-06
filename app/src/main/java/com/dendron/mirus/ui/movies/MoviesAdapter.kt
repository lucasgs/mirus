package com.dendron.mirus.ui.movies

import android.widget.ImageView
import android.widget.TextView
import com.dendron.mirus.ui.details.MovieUIModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class MoviesAdapter(itemClickedListener: (MovieUIModel, TextView, ImageView) -> Unit) : ListDelegationAdapter<List<MovieUIModel>>(
    moviesAdapterDelegate { movie, title, imageview ->
        itemClickedListener.invoke(
            movie, title, imageview
        )
    }
) {

    fun updateItems(movies: List<MovieUIModel>) {
        items = movies
        notifyDataSetChanged()
    }

}