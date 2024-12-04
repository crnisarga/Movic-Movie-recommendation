package com.example.movieapp

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String, // Image URL path for the movie poster
    val voteAverage: Double, // Movie rating
    val overview: String? // Movie description/overview (optional)
)
