package com.example.apizeldaparalelo.implementacionAPI

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class GameViewModel(private val api: ApiServices) : ViewModel() {

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> get() = _games

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchGames(limit: Int = 20) {
        viewModelScope.launch {
            try {
                val response = api.getGames(limit)

                // Imprimir la respuesta de la API para verificar los datos en Logcat
                Log.d("GameViewModel", "API Response: $response")

                if (response.success) {
                    _games.value = response.data
                } else {
                    _error.value = "Error: No se pudieron cargar los juegos"
                    Log.e("GameViewModel", "Error en la respuesta: ${response.success}")
                }
            } catch (e: Exception) {
                // Aquí se maneja el error si la API falla o si hay problemas de red
                _error.value = "Error: ${e.message}"
                Log.e("GameViewModel", "Error al realizar la solicitud: ${e.message}", e)
            }
        }
    }
}
