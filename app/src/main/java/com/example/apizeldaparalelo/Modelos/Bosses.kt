package com.example.apizeldaparalelo.Modelos

data class BossesResponse(
    val success: Boolean,
    val count: Int,
    val data: List<Boss>
)



data class Boss(
    val name: String,
    val description: String,
    val appearances: List<String>,
    val gender: String? = null,  // Opcional
    val race: String? = null,    // Opcional
    val id: String
)

