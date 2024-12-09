package com.example.apizeldaparalelo.Modelos

data class MonsterResponse(
    val success: Boolean,
    val count: Int,
    val data: List<Monster>
)

data class Monster(
    val id: String,
    val name: String,
    val description: String,
    val appearances: List<String>,
    val gameNames: List<String> = emptyList() // Lista de nombres de juegos
)

