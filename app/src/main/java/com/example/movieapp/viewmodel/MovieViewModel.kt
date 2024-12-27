package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.data.PopularMoviesResponse
import com.example.movieapp.model.data.Movie
import com.example.movieapp.model.data.MovieResponse
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieViewModel (private val repository: MovieRepository) : ViewModel(){

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchPopularMovies(genreId: Int, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<PopularMoviesResponse> = repository.getPopularMovies(genreId, apiKey)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        _popularMovies.value = response.body()?.results ?: emptyList()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                    _errorMessage.value = "Failed to fetch popular movies"
                        }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Network error: ${e.message}"
                }
            }
        }
    }

    fun searchMovies(query: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<MovieResponse> = repository.searchMovies(query, apiKey)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        _searchResults.value = response.body()?.results ?: emptyList()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "Error fetching search results"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Network error: ${e.message}"
                }
            }
        }
    }
}