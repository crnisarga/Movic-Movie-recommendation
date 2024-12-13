package com.example.movieapp.ui


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(MovieRepository(RetrofitClient.retrofit.create(ApiService::class.java)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvMovies)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchView = findViewById(R.id.searchView)


        movieViewModel.popularMovies.observe(this, Observer { movies ->
            movieAdapter = MovieAdapter(movies)
            recyclerView.adapter = movieAdapter
        })

        movieViewModel.searchResults.observe(this, Observer { searchResults ->
            if (searchResults.isNotEmpty()) {
                movieAdapter = MovieAdapter(searchResults)
                recyclerView.adapter = movieAdapter
            } else {
                Toast.makeText(this, "No movies found", Toast.LENGTH_SHORT).show()
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
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        movieViewModel.fetchPopularMovies(apiKey)
    }
}