package com.dendron.mirus.ui.movies

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dendron.mirus.R
import com.dendron.mirus.ui.details.MovieDetailActivity
import com.dendron.mirus.ui.details.MovieUIModel
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.content_movies.*

class MoviesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MoviesAdapter
    private lateinit var viewManager: GridLayoutManager

    private val moviesViewModel: MoviesViewModel by lazy {
        ViewModelProviders.of(this).get(MoviesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)

        viewManager = GridLayoutManager(this, 2)

        viewAdapter = MoviesAdapter { model, title, poster ->

            val updatedModel =
                model.copy(isFavorite = moviesViewModel.isFavoriteMovie(model.movie))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                val titlePair = androidx.core.util.Pair.create(title as View, "title")
                val posterPair = androidx.core.util.Pair.create(poster as View, "poster")

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this, posterPair
                )

                startActivity(
                    Intent(this, MovieDetailActivity::class.java)
                        .putExtra(MOVIE_DATA, updatedModel)
                    , options.toBundle()
                )
            } else {

                startActivity(
                    Intent(this, MovieDetailActivity::class.java)
                        .putExtra(MOVIE_DATA, updatedModel)
                )
            }
        }

        recyclerView = findViewById<RecyclerView>(R.id.rvMovies).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    if (!recyclerView.canScrollVertically(1) && dy != 0) {
                        moviesViewModel.getMoreMovies()
                    }
                }
            })
        }

        moviesViewModel.state.observe(this, Observer { state ->
            showSectionTitle(state.section.name)
            showMovies(state.movies)
            setFavoriteIcon(state.showFavorites)
        })

        fab.setOnClickListener {
            if (it.tag != null) {
                moviesViewModel.toggleFavoritesMovies(it.tag as Boolean)
            }
        }
    }

    private fun setFavoriteIcon(showFavorites: Boolean) {
        if (showFavorites) {
            fab.setImageResource(R.drawable.ic_home_white)
        } else {
            fab.setImageResource(R.drawable.ic_favorite_white)
        }
        fab.tag = showFavorites
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
                    moviesViewModel.searchMovies(newText)
                    return true
                }

            })
        }

        return true
    }

    private fun showSectionTitle(title: String) {
        tvSectionTitle.text = title
    }

    private fun showMovies(movies: List<MovieUIModel>?) {
        movies?.let {

            recyclerView.isGone = movies.isEmpty()
            tvEmptyState.isGone = !recyclerView.isGone

            viewAdapter.updateItems(movies)
        }

    }

    companion object {
        const val MOVIE_DATA = "movie_data"
    }

}
