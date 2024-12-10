package com.example.apizeldaparalelo.implementacionAPI

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apizeldaparalelo.implementacionAPI.ApiServices
import com.example.apizeldaparalelo.Modelos.Boss
import com.example.apizeldaparalelo.Modelos.MonsterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BossesViewModel(private val api: ApiServices) : ViewModel() {

    private val _bosses = MutableStateFlow<List<Boss>>(emptyList())
    val bosses: StateFlow<List<Boss>> get() = _bosses

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    // Función para obtener los bosses
    fun fetchBosses(limit: Int = 20) {
        viewModelScope.launch {
            try {
                // Llamada al endpoint para obtener los Bosses
                val response = api.getBosses(limit)

                Log.d("BossesViewModel", "API Response: $response")

                if (response.success) {
                    _bosses.value = response.data // Asignar los bosses a la lista
                } else {
                    _error.value = "Error: No se pudieron cargar los bosses"
                    Log.e("BossesViewModel", "Error en la respuesta: ${response.success}")
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                Log.e("BossesViewModel", "Error al realizar la solicitud: ${e.message}", e)
            }
        }
    }
}

