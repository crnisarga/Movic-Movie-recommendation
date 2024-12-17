package com.example.movieapp.ui

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.data.Movie
import com.example.movieapp.model.network.ApiService
import com.example.movieapp.model.network.RetrofitClient
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.viewmodel.MovieDetailsViewModel
import com.example.movieapp.viewmodel.MovieViewModelFactory

class MovieDetailActivity : AppCompatActivity() {

    private val apiKey = "0863915f80e3f1e7ce3b2bc508d31e83"
    private lateinit var movieImage: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieDescription: TextView
    private lateinit var movieRating: TextView

    private val movieDetailsViewModel : MovieDetailsViewModel by viewModels {
        MovieViewModelFactory(MovieRepository(RetrofitClient.retrofit.create(ApiService::class.java)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movieTitle = findViewById(R.id.movieTitle)
        movieDescription = findViewById(R.id.movieDescription)
        movieRating = findViewById(R.id.movieRating)
        movieImage = findViewById(R.id.movieImage)

        val movieId = intent.getIntExtra("movie_id",0)

        movieDetailsViewModel.getMovieDetail(movieId,apiKey)

        movieDetailsViewModel.movieDetail.observe(this, { movie ->
            updateUI(movie)
            Log.d("ncr","the value fetched id ${movie.title} ${movie.overview}")
        })
    }
    private fun updateUI(movie: Movie) {
        movieTitle.text = movie.title
        movieDescription.text = movie.overview
        movieRating.text = movie.vote_average.toString()

        val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

        Glide.with(this)
            .load(imageUrl) // URL or resource to load
            .placeholder(R.drawable.ic_launcher_background) // Optional: A placeholder while the image loads
            .error(R.drawable.ic_launcher_foreground) // Optional: An image if the loading fails
            .into(movieImage) // Bind it to the ImageView

    }
}