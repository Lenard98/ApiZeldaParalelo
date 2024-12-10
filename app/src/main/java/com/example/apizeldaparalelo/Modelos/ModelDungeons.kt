package com.example.apizeldaparalelo.Modelos

data class Dungeon(
    val id: String,
    val name: String,
    val description: String,
    val appearances: List<String>
)

data class DungeonsResponse(
    val success: Boolean,
    val count: Int,
    val data: List<Dungeon>
)