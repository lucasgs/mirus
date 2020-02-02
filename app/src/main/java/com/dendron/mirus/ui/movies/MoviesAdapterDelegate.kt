package com.dendron.mirus.ui.movies

import android.widget.ImageView
import android.widget.TextView
import com.dendron.mirus.R
import com.dendron.mirus.model.Movie
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.squareup.picasso.Picasso

fun moviesAdapterDelegate(itemClickedListener : (Movie) -> Unit) = adapterDelegate<Movie, Movie>(R.layout.item_movie) {

    val title : TextView = findViewById(R.id.tvTitle)
    val poster : ImageView = findViewById(R.id.ivPoster)

    title.setOnClickListener { itemClickedListener(item) }
    poster.setOnClickListener { itemClickedListener(item) }

    bind { diffPayloads -> // diffPayloads is a List<Any> containing the Payload from your DiffUtils
        title.text = item.title
        Picasso.get().load(item.posterPath).into(poster)
    }

}