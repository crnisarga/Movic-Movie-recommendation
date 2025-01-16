package com.example.movieapp.ui


import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.model.network.ApiService
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.viewmodel.MovieViewModelFactory
import com.example.movieapp.R
import com.example.movieapp.model.network.RetrofitClient

class MainActivity : AppCompatActivity() {

    private val apiKey = "0863915f80e3f1e7ce3b2bc508d31e83"
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var searchView: SearchView
    private lateinit var fragmentContainer: FrameLayout

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(MovieRepository(RetrofitClient.retrofit.create(ApiService::class.java)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        recyclerView = findViewById(R.id.rvMovies)
//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        fragmentContainer = findViewById(R.id.fragment_container)

        searchView = findViewById(R.id.searchView)

//        movieAdapter = MovieAdapter()
//        recyclerView.adapter = movieAdapter

        val genreId = intent.getIntExtra("genre_id",0)

//        movieViewModel.popularMovies.observe(this, Observer { movies ->
//            movieAdapter.submitList(movies)
//        })

        movieViewModel.searchResults.observe(this, Observer { searchResults ->
            if (searchResults.isNotEmpty()) {
                movieAdapter.submitList(searchResults)
            } else {
                showNoMoviesFoundFragment()
            }
        })

        movieViewModel.errorMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    movieViewModel.searchMovies(it, apiKey)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

//        movieViewModel.fetchPopularMovies(genreId,apiKey)
    }

    private fun showNoMoviesFoundFragment() {
        recyclerView.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, NoMoviesFoundFragment(), "NoMoviesFoundFragment")
        transaction.commit()
    }


    override fun onBackPressed() {

        searchView.setQuery("", false)

        // Collapse the SearchView (to its default state)
        searchView.onActionViewCollapsed()
        val fragment = supportFragmentManager.findFragmentByTag("NoMoviesFoundFragment")

        if (fragment != null && fragment.isVisible) {
            fragmentContainer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        else {
            super.onBackPressed()
        }
    }

}