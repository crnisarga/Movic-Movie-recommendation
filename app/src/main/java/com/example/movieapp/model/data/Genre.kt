package com.example.movieapp.model.data

import androidx.room.Entity

@Entity(tableName = "genre_table")
data class Genre(
    val id: Int,
    val name: String
)

