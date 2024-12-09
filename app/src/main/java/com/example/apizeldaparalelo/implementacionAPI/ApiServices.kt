package com.example.apizeldaparalelo.implementacionAPI

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("games")
    suspend fun getGames(
        @Query("limit") limit: Int = 20
    ): GameResponse
}


