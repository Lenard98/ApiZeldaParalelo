package com.example.apizeldaparalelo.Modelos

data class CharacterResponse(
    val success: Boolean,
    val count: Int,
    val data: List<Character>
)

data class Character(
    val id: String,
    val name: String,
    val description: String,
    val gender: String?,
    val race: String?,
    val appearances: List<String>
)
