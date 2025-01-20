package com.example.movieapp.model.network

import android.content.Context
import androidx.room.Room
import com.example.movieapp.model.data.MovieDatabase

object DatabaseProvider {
    @Volatile
    private var INSTANCE: MovieDatabase? = null

    fun getDatabase(context: Context): MovieDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "movie-database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
