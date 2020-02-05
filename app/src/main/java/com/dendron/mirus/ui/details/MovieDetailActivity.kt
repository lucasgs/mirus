package com.dendron.mirus.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.dendron.mirus.R
import com.dendron.mirus.model.Movie
import com.dendron.mirus.ui.movies.MoviesActivity.Companion.MOVIE_DATA
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private val moviesDetailViewModel: MovieDetailViewModel by lazy {
        ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        intent?.let {

            intent.getParcelableExtra<MovieUIModel>(MOVIE_DATA)?.let { model ->

                val movie = model.movie

                val picasso = Picasso.get()

                picasso
                    .load(movie.backDropPath)
                    .into(movie_backdrop)

                picasso
                    .load(movie.posterPath)
                    .fit()
                    .into(movie_poster)

                movie_poster.setOnClickListener { openPicture(movie.posterPath) }

                movie_title.text = movie.title
                movie_rating.rating = (movie.voteAverage / 2).toFloat()
                movie_release_date.text = movie.releaseDate
                movie_overview.text = movie.overview

                movie_favorite.setOnClickListener { moviesDetailViewModel.saveFavoriteMovie(movie) }
            }
        }
    }

    private fun openPicture(imagePath: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.parse(imagePath), "image/*")
        startActivity(intent)
    }

}
