package com.example.movieapp.model.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitInstance {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")  // Replace with your base URL
        .addConverterFactory(GsonConverterFactory.create())  // Use Gson for JSON parsing
        .client(client)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
