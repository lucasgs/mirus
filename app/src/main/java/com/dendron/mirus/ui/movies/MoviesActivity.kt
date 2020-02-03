package com.dendron.mirus.ui.movies

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dendron.mirus.R
import com.dendron.mirus.model.Movie
import com.dendron.mirus.ui.details.MovieDetailActivity

import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MoviesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        viewManager = GridLayoutManager(this, 2)
        viewAdapter = MoviesAdapter { movie ->

            startActivity(
                Intent(this, MovieDetailActivity::class.java)
                    .putExtra(MOVIE_BACKDROP, movie.backDropPath)
                    .putExtra(MOVIE_POSTER, movie.posterPath)
                    .putExtra(MOVIE_TITLE, movie.title)
                    .putExtra(MOVIE_VOTE_AVERAGE, movie.voteAverage)
                    .putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
                    .putExtra(MOVIE_OVERVIEW, movie.overview)
            )
        }

        recyclerView = findViewById<RecyclerView>(R.id.rvMovies).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }

        homeViewModel.movies.observe(this, Observer { movies ->
            showMovies(movies)
        })


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun showMovies(movies: List<Movie>?) {
        movies?.let {
            viewAdapter.updateItems(movies)
        }

    }

    companion object {
        const val MOVIE_BACKDROP = "extra_movie_backdrop"
        const val MOVIE_POSTER = "extra_movie_poster"
        const val MOVIE_TITLE = "extra_movie_title"
        const val MOVIE_VOTE_AVERAGE = "extra_movie_vote_average"
        const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
        const val MOVIE_OVERVIEW = "extra_movie_overview"
    }

}
