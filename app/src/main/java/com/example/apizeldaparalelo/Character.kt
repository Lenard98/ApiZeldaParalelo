package com.example.apizeldaparalelo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Snackbar
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.style.TextOverflow
import com.example.apizeldaparalelo.Modelos.Character
import com.example.apizeldaparalelo.implementacionAPI.CharacterViewModel
import com.example.apizeldaparalelo.implementacionAPI.GameViewModel


@Composable
fun CharacterScreen(viewModel: CharacterViewModel, gameViewModel: GameViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val characters by viewModel.characters.collectAsState(initial = emptyList())
    val errorMessage by viewModel.error.collectAsState()
    val games by gameViewModel.games.collectAsState(initial = emptyList())

    // Crear un mapa de las URLs a los nombres de los juegos
    val gameUrlToNameMap = games.associateBy { it.id }.mapValues { it.value.name }

    // Fondo de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar personajes") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar mensaje de error si existe
        errorMessage?.let {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                content = { Text(text = it) }
            )
        }

        // Lista de personajes
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val filteredCharacters = characters.filter {
                it.name.contains(searchQuery.text, ignoreCase = true)
            }

            items(filteredCharacters) { character ->
                // Mapear las apariciones a los nombres de los juegos
                val gameNames = character.appearances.mapNotNull { url ->
                    val gameId = url.split("/").last()
                    gameUrlToNameMap[gameId]
                }

                CharacterItem(character.copy(appearances = gameNames))
            }
        }
    }

    // Llamada inicial para obtener datos
    LaunchedEffect(Unit) {
        viewModel.fetchCharacters()
        gameViewModel.fetchGames() // Asegurarse de tener la lista de juegos
    }
}

@Composable
fun CharacterItem(character: Character) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = character.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar género y raza si están disponibles
            if (character.gender != null) {
                Text(
                    text = "Género: ${character.gender}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (character.race != null) {
                Text(
                    text = "Raza: ${character.race}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Mostrar nombres de juegos
            character.appearances.forEach { gameName ->
                Text(
                    text = "Aparece en: $gameName",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
