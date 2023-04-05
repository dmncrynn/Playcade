package com.example.test2.network

import com.example.test2.model.Words
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiService {
    @GET("/api/v1/words")
    suspend fun getWords(): List<Words>
}

object ApiClient {
    private const val BASE_URL = "https://www.wordgamedb.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

}
