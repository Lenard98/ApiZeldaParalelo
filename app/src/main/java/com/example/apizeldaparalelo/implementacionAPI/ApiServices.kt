package com.example.apizeldaparalelo.implementacionAPI

import com.example.apizeldaparalelo.Modelos.BossesResponse
import com.example.apizeldaparalelo.Modelos.CharacterResponse
import com.example.apizeldaparalelo.Modelos.DungeonsResponse
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

    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int = 20
    ): CharacterResponse

    @GET("dungeons")
    suspend fun getDungeons(
        @Query("limit") limit: Int = 20
    ): DungeonsResponse

    @GET("bosses")
    suspend fun getBosses(
        @Query("limit") limit: Int = 20
    ): BossesResponse

}






