package com.example.movieapp

data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String, // Image URL path for the movie poster
    val vote_average: Double, // Movie rating
)

