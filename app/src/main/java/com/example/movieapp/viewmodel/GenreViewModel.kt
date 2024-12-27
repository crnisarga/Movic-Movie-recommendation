package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.data.Genre
import com.example.movieapp.model.data.GenreResponse
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class GenreViewModel (private val repository: MovieRepository) : ViewModel() {

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> = _genres

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

     fun fetchGenres(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<GenreResponse> = repository.getGenres(apiKey)
                if (response.isSuccessful)  {
                    withContext(Dispatchers.Main) {
                        _genres.value = response.body()?.genres ?: emptyList()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "Failed to fetch popular movies"
                    }
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Network error: ${e.message}"
                }
            }
        }
    }

}