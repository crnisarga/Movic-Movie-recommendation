package com.example.movieapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.app.AppCompatActivity

private val apiKey = "0863915f80e3f1e7ce3b2bc508d31e83"

class MainActivity : AppCompatActivity() {

    private val apiKey = "0863915f80e3f1e7ce3b2bc508d31e83"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.getPopularMovies(apiKey)

        call.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(call: Call<PopularMoviesResponse>, response: Response<PopularMoviesResponse>) {
                if (response.isSuccessful) {
                    val movieList = response.body()?.results ?: emptyList()
                    println("The output is $movieList")
                    Log.e("MainActivity", "the output is successful")
                } else {
                    Log.e("MainActivity", "the output is not successful")
                    println("There is an error issue")
                }
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network Error", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "Error: ${t.message}")
            }
        })
    }
}
