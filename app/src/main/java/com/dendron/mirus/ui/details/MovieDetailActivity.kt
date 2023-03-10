package com.dendron.mirus.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.dendron.mirus.R
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
                    .into(movie_poster)

                movie_poster.setOnClickListener { openPicture(movie.posterPath) }

                movie_title.text = movie.title
                movie_rating.rating = (movie.voteAverage / 2).toFloat()
                movie_release_date.text = movie.releaseDate
                movie_overview.text = movie.overview

                movie_favorite.isChecked = model.isFavorite

                val scaleAnimation = ScaleAnimation(
                    0f,
                    1f,
                    0f,
                    1f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
                ).apply {
                    duration = 800
                    interpolator = BounceInterpolator()
                }

                movie_favorite.setOnClickListener {

                    if (movie_favorite.isChecked) {
                        it.startAnimation(scaleAnimation)
                    }

                    moviesDetailViewModel.saveFavoriteMovie(
                        model.copy(
                            isFavorite = movie_favorite.isChecked
                        )
                    )
                }
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
