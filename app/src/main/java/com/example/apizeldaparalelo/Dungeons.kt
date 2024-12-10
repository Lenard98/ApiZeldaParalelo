package com.example.apizeldaparalelo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.apizeldaparalelo.Modelos.Dungeon
import com.example.apizeldaparalelo.implementacionAPI.DungeonViewModel
import com.example.apizeldaparalelo.implementacionAPI.GameViewModel

@Composable
fun DungeonScreen(viewModel: DungeonViewModel, gameViewModel: GameViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val dungeons by viewModel.dungeons.collectAsState(initial = emptyList())
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
            label = { Text("Buscar dungeons") },
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

        // Lista de dungeons
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val filteredDungeons = dungeons.filter {
                it.name.contains(searchQuery.text, ignoreCase = true)
            }

            items(filteredDungeons) { dungeon ->
                // Mapear las apariciones a los nombres de los juegos
                val gameNames = dungeon.appearances.mapNotNull { url ->
                    val gameId = url.split("/").last()
                    gameUrlToNameMap[gameId]
                }

                DungeonItem(dungeon.copy(appearances = gameNames))
            }
        }
    }

    // Llamada inicial para obtener datos
    LaunchedEffect(Unit) {
        viewModel.fetchDungeons()
        gameViewModel.fetchGames() // Asegurarse de tener la lista de juegos
    }
}


@Composable
fun DungeonItem(dungeon: Dungeon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = dungeon.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = dungeon.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar los juegos donde aparece el dungeon
            dungeon.appearances.forEach { gameName ->
                Text(
                    text = "Aparece en: $gameName",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}