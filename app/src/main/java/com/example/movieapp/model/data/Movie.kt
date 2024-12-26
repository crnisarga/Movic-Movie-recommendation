package com.example.movieapp.model.data

data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String,
    val vote_average: Double,
    val overview: String,
    val genre_ids: List<Int>
)

