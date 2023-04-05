package com.example.test2.network

import com.example.test2.model.TriviaResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService1 {
    @GET("api.php?amount=100&type=multiple")
    suspend fun getTrivia(): TriviaResponse
}

object ApiClient1 {
    private const val BASE_URL = "https://opentdb.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService1 = retrofit.create(ApiService1::class.java)
}