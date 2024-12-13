package com.example.movieapp

import retrofit2.Call
import retrofit2.Response

class MovieRepository(private val apiService: ApiService) {

    suspend fun getPopularMovies(apiKey: String): Response<PopularMoviesResponse> {
        return apiService.getPopularMovies(apiKey)
    }

    suspend fun searchMovies(query: String, apiKey: String): Response<MovieResponse> {
        return apiService.searchMovie(query, apiKey)
    }
}