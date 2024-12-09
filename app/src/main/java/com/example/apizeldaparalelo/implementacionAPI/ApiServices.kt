package com.example.apizeldaparalelo.implementacionAPI

import com.example.apizeldaparalelo.Modelos.GameResponse
import com.example.apizeldaparalelo.Modelos.MonsterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("games")
    suspend fun getGames(
        @Query("limit") limit: Int = 20
    ): GameResponse

    @GET("monsters")
    suspend fun getMonsters(
        @Query("limit") limit: Int = 20
    ): MonsterResponse
}






