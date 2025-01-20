package com.example.movieapp.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val release_date: String,
    val overview: String,
    val poster_path: String
)