package com.example.apizeldaparalelo.Modelos
data class GameResponse(
    val success: Boolean,
    val count: Int,
    val data: List<Game>
)

data class Game(
    val id: String,
    val name: String,
    val description: String,
    val developer: String,
    val publisher: String,
    val released_date: String
)

