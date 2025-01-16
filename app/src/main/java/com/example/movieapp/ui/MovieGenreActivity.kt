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
import com.example.movieapp.model.data.Genre
import com.example.movieapp.model.data.Movie
import com.example.movieapp.model.network.ApiService
import com.example.movieapp.model.network.RetrofitClient
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.viewmodel.GenreViewModel
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.viewmodel.MovieViewModelFactory

class MovieGenreActivity : AppCompatActivity() {

    private val apiKey = "0863915f80e3f1e7ce3b2bc508d31e83"
    private lateinit var recyclerView: RecyclerView
    private lateinit var genreAdapter: GenreAdapter

    private val genreViewModel: GenreViewModel by viewModels {
        MovieViewModelFactory(MovieRepository(RetrofitClient.retrofit.create(ApiService::class.java)))
    }

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(MovieRepository(RetrofitClient.retrofit.create(ApiService::class.java)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_genre)

        Log.d("ncr","the movie screen is launched")

        recyclerView = findViewById(R.id.genres_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        genreAdapter = GenreAdapter(emptyMap())
        recyclerView.adapter = genreAdapter

        movieViewModel.popularMovies.observe(this, Observer { movies ->
            // Here we assume movies are updated for a specific genre, so update the map
            Log.d("ncr","the movie value is ${movies}")
            updateMovieListMap()
        })

        genreViewModel.genres.observe(this, Observer { genres ->
            if (genres.isEmpty()) {
                Toast.makeText(this, "No genres available", Toast.LENGTH_SHORT).show()
            } else {
                fetchMoviesForGenres(genres)
                Log.d("ncr","the genre data is ${genres}")
                genreAdapter.submitList(genres)
            }
        })


        genreViewModel.fetchGenres(apiKey)
    }

    private fun fetchMoviesForGenres(genres: List<Genre>) {
        // Start fetching movies for each genre
        val genreIdList = genres.map { it.id }
        Log.d("ncr","the genre id value are ${genreIdList}")
        genreIdList.forEach { genreId ->
            Log.d("ncr","the genre id are ${genreId}")
            movieViewModel.fetchPopularMovies(genreId,apiKey)
        }
    }

    private fun updateMovieListMap() {
        // Combine genres and movie lists into a map
        val movieListMap = mutableMapOf<Int, List<Movie>>()

        genreViewModel.genres.value?.forEach { genre ->
            // You could filter out the movies related to this genre based on the genre id
            val relatedMovies = movieViewModel.popularMovies.value?.filter { it.genre_ids.contains(genre.id) }
            if (relatedMovies != null) {
                movieListMap[genre.id] = relatedMovies
            }
        }
        Log.d("ncr","the movie list map is ${movieListMap}")

        genreAdapter.updateMovieListMap(movieListMap)
    }
}
