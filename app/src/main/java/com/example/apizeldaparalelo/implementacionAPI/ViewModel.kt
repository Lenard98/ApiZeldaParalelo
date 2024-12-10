package com.example.apizeldaparalelo.implementacionAPI

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apizeldaparalelo.Modelos.Game
import com.example.apizeldaparalelo.Modelos.Monster
import com.example.apizeldaparalelo.Modelos.Character
import com.example.apizeldaparalelo.Modelos.Dungeon
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

class MonsterViewModel(private val api: ApiServices) : ViewModel() {

    private val _monsters = MutableStateFlow<List<Monster>>(emptyList())
    val monsters: StateFlow<List<Monster>> get() = _monsters

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchMonsters(limit: Int = 20) {
        viewModelScope.launch {
            try {
                val response = api.getMonsters(limit)
                if (response.success) {
                    _monsters.value = response.data
                } else {
                    _error.value = "Error: No se pudieron cargar los monstruos"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }
}

class CharacterViewModel(private val api: ApiServices) : ViewModel() {

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> get() = _characters

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchCharacters(limit: Int = 20) {
        viewModelScope.launch {
            try {
                val response = api.getCharacters(limit)

                // Imprimir la respuesta de la API para verificar los datos en Logcat
                Log.d("CharacterViewModel", "API Response: $response")

                if (response.success) {
                    _characters.value = response.data
                } else {
                    _error.value = "Error: No se pudieron cargar los personajes"
                    Log.e("CharacterViewModel", "Error en la respuesta: ${response.success}")
                }
            } catch (e: Exception) {
                // Aquí se maneja el error si la API falla o si hay problemas de red
                _error.value = "Error: ${e.message}"
                Log.e("CharacterViewModel", "Error al realizar la solicitud: ${e.message}", e)
            }
        }
    }
}

class DungeonViewModel(private val api: ApiServices) : ViewModel() {

    private val _dungeons = MutableStateFlow<List<Dungeon>>(emptyList())
    val dungeons: StateFlow<List<Dungeon>> get() = _dungeons

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchDungeons(limit: Int = 20) {
        viewModelScope.launch {
            try {
                val response = api.getDungeons(limit)

                Log.d("DungeonViewModel", "API Response: $response")

                if (response.success) {
                    _dungeons.value = response.data
                } else {
                    _error.value = "Error: No se pudieron cargar los dungeons"
                    Log.e("DungeonViewModel", "Error en la respuesta: ${response.success}")
                }
            } catch (e: Exception) {

                _error.value = "Error: ${e.message}"
                Log.e("DungeonViewModel", "Error al realizar la solicitud: ${e.message}", e)
            }
        }
    }
}

