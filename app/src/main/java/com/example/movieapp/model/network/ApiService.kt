package com.example.movieapp.model.network

import com.example.movieapp.model.data.GenreResponse
import com.example.movieapp.model.data.Movie
import com.example.movieapp.model.data.MovieResponse
import com.example.movieapp.model.data.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getPopularMovies( @Query("genres") genreId: Int,@Query("api_key") apiKey: String): Response<PopularMoviesResponse>

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String, @Query("api_key") apiKey: String)
    : Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Movie

    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey: String): Response<GenreResponse>
}