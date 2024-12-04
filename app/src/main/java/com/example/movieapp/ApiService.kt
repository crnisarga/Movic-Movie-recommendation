package com.example.movieapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<PopularMoviesResponse>
}