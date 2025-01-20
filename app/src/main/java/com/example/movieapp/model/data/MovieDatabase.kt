package com.example.movieapp.model.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.model.network.MovieDao

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
