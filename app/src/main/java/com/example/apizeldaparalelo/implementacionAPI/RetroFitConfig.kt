package com.example.apizeldaparalelo.implementacionAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitConfig {
    private const val BASE_URL = "https://zelda.fanapis.com/api/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}



