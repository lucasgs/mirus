package com.dendron.mirus.ui.movies

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dendron.mirus.R
import com.dendron.mirus.model.Movie
import com.dendron.mirus.ui.details.MovieDetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.content_movies.*

class MoviesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MoviesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)

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

        homeViewModel.movies.observe(this, Observer { state ->
            showSectionTitle(state.sectionName)
            showMovies(state.movies)
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        (menu?.findItem(R.id.search)?.actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = false // Do not iconify the widget; expand it by default

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {

                    homeViewModel.searchMovies(newText)

                    return false
                }

            })
        }

        return true
    }

    private fun showSectionTitle(title: String) {
        tvSectionTitle.text = title
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
