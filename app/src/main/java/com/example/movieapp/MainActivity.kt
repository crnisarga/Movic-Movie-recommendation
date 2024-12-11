package com.example.movieapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val apiKey = "0863915f80e3f1e7ce3b2bc508d31e83"
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvMovies)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchView = findViewById(R.id.searchView)


        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    // Call the API to check if the movie is present
                    hideKeyboard()
                    checkMoviePresence(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


        fetchPopularMovies()
    }


    private fun checkMoviePresence(query: String) {
        // Show a loading indicator if needed
        lifecycleScope.launch {
            try {

                val apiService = RetrofitClient.retrofit.create(ApiService::class.java)

                val response: Response<MovieResponse> = apiService.searchMovie(
                    query,
                    apiKey
                )

                if (response.isSuccessful) {
                    val movieResults = response.body()?.results ?: emptyList()
                    if (movieResults.isNotEmpty()) {
                        movieAdapter = MovieAdapter(movieResults)
                        recyclerView.adapter = movieAdapter
                    } else {
                        // Movie found
                        Toast.makeText(
                            this@MainActivity,
                            "Movie found: ${movieResults[0].title}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Handle error response
                    Toast.makeText(this@MainActivity, "Error fetching data", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                // Handle network errors
                Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun fetchPopularMovies() {
        // Create Retrofit instance
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)

        // Make the network request
        val call = apiService.getPopularMovies(apiKey)

        call.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val movieList = response.body()?.results ?: emptyList()
                    Log.e("MainActivity", "Error the result  got $movieList")
                    movieAdapter = MovieAdapter(movieList)
                    recyclerView.adapter = movieAdapter

                } else {
                    Log.e("MainActivity", "Error the result not got")
                    Toast.makeText(this@MainActivity, "Failed to fetch movies", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network Error", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "Error: ${t.message}")
            }
        })
    }
}
