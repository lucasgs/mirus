package com.dendron.mirus.ui.movies

import android.widget.ImageView
import android.widget.TextView
import com.dendron.mirus.model.Movie
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class MoviesAdapter(itemClickedListener: (Movie, TextView, ImageView) -> Unit) : ListDelegationAdapter<List<Movie>>(
    moviesAdapterDelegate { movie, title, imageview ->
        itemClickedListener.invoke(
            movie, title, imageview
        )
    }
) {

    fun updateItems(movies: List<Movie>) {
        items = movies
        notifyDataSetChanged()
    }

}