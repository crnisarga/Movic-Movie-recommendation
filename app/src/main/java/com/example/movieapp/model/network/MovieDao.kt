package com.example.movieapp.model.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.model.data.MovieEntity

@Dao
interface MovieDao {

    @Insert
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()
}