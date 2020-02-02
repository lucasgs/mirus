package com.dendron.mirus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dendron.mirus.R
import com.dendron.mirus.model.Movie

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MoviesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        //viewManager = LinearLayoutManager(activity)
        viewManager = GridLayoutManager(activity, 2)
        viewAdapter = MoviesAdapter{
            Toast.makeText(activity!!, "Clicked on ${it.id}: ${it.title}", Toast.LENGTH_LONG).show()
        }

        recyclerView = root.findViewById<RecyclerView>(R.id.rvMovies).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }

        homeViewModel.movies.observe(this, Observer { movies ->
            showMovies(movies)
        })

        return root
    }

    private fun showMovies(movies: List<Movie>?) {
        movies?.let {
            viewAdapter.updateItems(movies)
        }

    }
}