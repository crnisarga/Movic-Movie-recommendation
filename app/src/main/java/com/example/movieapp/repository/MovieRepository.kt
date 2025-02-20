package com.example.movieapp.repository

import com.example.movieapp.model.data.GenreResponse
import com.example.movieapp.model.data.Movie
import com.example.movieapp.model.network.ApiService
import com.example.movieapp.model.data.MovieResponse
import com.example.movieapp.model.data.PopularMoviesResponse
import retrofit2.Response

class MovieRepository(private val apiService: ApiService) {

    suspend fun getPopularMovies(genreId: Int, apiKey: String): Response<PopularMoviesResponse> {
        return apiService.getPopularMovies(genreId, apiKey)
    }

    suspend fun searchMovies(query: String, apiKey: String): Response<MovieResponse> {
        return apiService.searchMovie(query, apiKey)
    }

    suspend fun getMovieDetail(movieId: Int, apiKey: String):  Movie {
        return apiService.getMovieDetail(movieId, apiKey)
    }

    suspend fun getGenres(apiKey: String): Response<GenreResponse> {
        return apiService.getGenres(apiKey)
    }
}