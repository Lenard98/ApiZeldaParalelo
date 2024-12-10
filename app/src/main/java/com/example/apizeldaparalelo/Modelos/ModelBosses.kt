package com.example.apizeldaparalelo.Modelos

data class BossesResponse(
    val success: Boolean,
    val count: Int,
    val data: List<Bosses>
)

data class Bosses(
    val id: String,
    val name: String,
    val description: String,
    val appearances: List<String>,
    val dungeons: List<String>
)
