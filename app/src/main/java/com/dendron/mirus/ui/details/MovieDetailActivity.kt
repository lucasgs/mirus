package com.dendron.mirus.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dendron.mirus.R
import com.dendron.mirus.ui.movies.MoviesActivity.Companion.MOVIE_BACKDROP
import com.dendron.mirus.ui.movies.MoviesActivity.Companion.MOVIE_OVERVIEW
import com.dendron.mirus.ui.movies.MoviesActivity.Companion.MOVIE_POSTER
import com.dendron.mirus.ui.movies.MoviesActivity.Companion.MOVIE_RELEASE_DATE
import com.dendron.mirus.ui.movies.MoviesActivity.Companion.MOVIE_TITLE
import com.dendron.mirus.ui.movies.MoviesActivity.Companion.MOVIE_VOTE_AVERAGE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        intent?.let {
            val picasso = Picasso.get()

            picasso
                .load(intent.getStringExtra(MOVIE_BACKDROP))
                .into(movie_backdrop)

            val posterPath = intent.getStringExtra(MOVIE_POSTER)
            picasso
                .load(posterPath)
                .fit()
                .into(movie_poster)

            movie_poster.setOnClickListener { openPicture(posterPath) }

            movie_title.text = intent.getStringExtra(MOVIE_TITLE)
            movie_rating.rating = (intent.getDoubleExtra(MOVIE_VOTE_AVERAGE, 0.0) / 2).toFloat()
            movie_release_date.text = intent.getStringExtra(MOVIE_RELEASE_DATE)
            movie_overview.text = intent.getStringExtra(MOVIE_OVERVIEW)
        }
    }

    private fun openPicture(imagePath: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.parse(imagePath), "image/*")
        startActivity(intent)
    }

}
