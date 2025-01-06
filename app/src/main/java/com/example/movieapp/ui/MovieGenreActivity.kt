package com.example.movieapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.network.ApiService
import com.example.movieapp.model.network.RetrofitClient
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.viewmodel.GenreViewModel
import com.example.movieapp.viewmodel.MovieViewModelFactory

class MovieGenreActivity : AppCompatActivity() {

    private val apiKey = "0863915f80e3f1e7ce3b2bc508d31e83"
    private lateinit var recyclerView: RecyclerView
    private lateinit var genreAdapter: GenreAdapter

    private val genreViewModel: GenreViewModel by viewModels {
        MovieViewModelFactory(MovieRepository(RetrofitClient.retrofit.create(ApiService::class.java)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_genre)


        recyclerView = findViewById(R.id.genres_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        genreAdapter = GenreAdapter()
        recyclerView.adapter = genreAdapter

        genreViewModel.genres.observe(this, Observer { genres ->
            if (genres.isEmpty()) {
                Toast.makeText(this, "No genres available", Toast.LENGTH_SHORT).show()
            } else {
                genreAdapter.submitList(genres) // Submit the list to update the adapter
            }
        })

        genreViewModel.fetchGenres(apiKey)
    }

}