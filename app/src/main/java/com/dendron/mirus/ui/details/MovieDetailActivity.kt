package com.dendron.mirus.ui.details

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.dendron.mirus.R
import com.dendron.mirus.ui.movies.MoviesActivity

import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)


        tvMovieTitle.text = intent.getStringExtra(MoviesActivity.TITLE)
//        summaryDetailTextView.text = intent.getStringExtra(MainActivity.SUMMARY)
//        releaseDateTextView.text = intent.getStringExtra(MainActivity.RELEASE_DATE)
//        ratingTextView.text = String.format(getString(R.string.rating), intent.getFloatExtra(MainActivity.RATING, 1f).roundToInt())
//        picasso
//            .load(RetrofitClient.TMDB_IMAGEURL + intent.getStringExtra(MainActivity.POSTER))
//            .error(R.drawable.iconfinder_movie_285656)
//            .into(detailImageView)
//        }

    }

}
