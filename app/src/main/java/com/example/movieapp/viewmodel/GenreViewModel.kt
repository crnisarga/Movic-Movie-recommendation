package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.data.Genre
import com.example.movieapp.model.data.GenreResponse
import com.example.movieapp.model.data.Movie
import com.example.movieapp.model.data.PopularMoviesResponse
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class GenreViewModel (private val repository: MovieRepository) : ViewModel() {

    private val _genres = MutableLiveData<List<Genre>>()
    val genre: LiveData<List<Genre>> = _genres

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

     fun fetchGenres(apiKey: String) {
        viewModelScope.launch {
            try {
                val response: Response<GenreResponse> = repository.getGenres(apiKey)
                if (response.isSuccessful)  {
                    _genres.value = response.body()?.results ?: emptyList()
                } else {
                    _errorMessage.value = "Failed to fetch popular movies"
                }
            }catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
                Log.d("ncr","${e.message}")
            }
        }
    }

}