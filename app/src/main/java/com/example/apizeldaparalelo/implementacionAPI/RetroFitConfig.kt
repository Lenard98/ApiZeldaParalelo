package com.example.apizeldaparalelo.implementacionAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://zelda.fanapis.com/api/games?limit=2" // Cambia a la URL base de tu API

    val retrofit: Retrofit =Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}