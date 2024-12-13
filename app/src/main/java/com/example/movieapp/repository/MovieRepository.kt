package com.example.movieapp.repository

import com.example.movieapp.model.network.ApiService
import com.example.movieapp.model.data.MovieResponse
import com.example.movieapp.model.data.PopularMoviesResponse
import retrofit2.Response

class MovieRepository(private val apiService: ApiService) {

    suspend fun getPopularMovies(apiKey: String): Response<PopularMoviesResponse> {
        return apiService.getPopularMovies(apiKey)
    }

    suspend fun searchMovies(query: String, apiKey: String): Response<MovieResponse> {
        return apiService.searchMovie(query, apiKey)
    }
}