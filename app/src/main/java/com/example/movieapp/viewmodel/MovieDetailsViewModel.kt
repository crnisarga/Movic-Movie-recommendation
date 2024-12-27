package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.data.Movie
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel (private val repository: MovieRepository) : ViewModel() {

    private val _movieDetail = MutableLiveData<Movie>()
    val movieDetail: LiveData<Movie> get() = _movieDetail

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getMovieDetail(movieId: Int,  apiKey: String): LiveData<Movie> {
        viewModelScope.launch(Dispatchers.IO)  {
            val movie = repository.getMovieDetail(movieId,apiKey)
            _movieDetail.postValue(movie)
        }
        return movieDetail
    }
}