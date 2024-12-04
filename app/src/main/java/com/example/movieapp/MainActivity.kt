package com.example.movieapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private val apiKey = "0863915f80e3f1e7ce3b2bc508d31e83"
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvMovies)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchPopularMovies()
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
