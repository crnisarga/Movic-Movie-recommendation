package com.example.movieapp.ui

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.example.movieapp.R
import com.example.movieapp.model.data.Movie
import com.example.movieapp.model.network.ApiService
import com.example.movieapp.model.network.RetrofitClient
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.viewmodel.MovieDetailsViewModel
import com.example.movieapp.viewmodel.MovieViewModelFactory

class MovieDetailActivity : AppCompatActivity() {

    private val apiKey = "0863915f80e3f1e7ce3b2bc508d31e83"
    private lateinit var movieImage: Image
    private lateinit var movieTitle: TextView
    private lateinit var movieDescription: TextView
    private lateinit var movieRating: TextView

    private val movieDetailsViewModel : MovieDetailsViewModel by viewModels {
        MovieViewModelFactory(MovieRepository(RetrofitClient.retrofit.create(ApiService::class.java)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra("movie_id",0)

        movieDetailsViewModel.getMovieDetail(movieId,apiKey)

        movieDetailsViewModel.movieDetail.observe(this, { movie ->
            updateUI(movie)
        })
    }


    private fun updateUI(movie: Movie) {
        movieTitle.text = movie.title
        movieDescription.text = movie.description
    }
}