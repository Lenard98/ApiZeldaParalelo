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
import com.example.apizeldaparalelo.Modelos.Bosses
import com.example.apizeldaparalelo.implementacionAPI.BossesViewModel
import com.example.apizeldaparalelo.implementacionAPI.DungeonViewModel
import com.example.apizeldaparalelo.implementacionAPI.GameViewModel

@Composable
fun BossesScreen(viewModel: BossesViewModel, gameViewModel: GameViewModel, dungeonViewModel: DungeonViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val bosses by viewModel.bosses.collectAsState(initial = emptyList())
    val errorMessage by viewModel.error.collectAsState()
    val games by gameViewModel.games.collectAsState(initial = emptyList())
    val dungeons by dungeonViewModel.dungeons.collectAsState(initial = emptyList())

    // Crear mapas de las URLs a nombres
    val gameUrlToNameMap = games.associateBy { it.id }.mapValues { it.value.name }
    val dungeonUrlToNameMap = dungeons.associateBy { it.id }.mapValues { it.value.name }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar bosses") },
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

        // Lista de bosses
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val filteredBosses = bosses.filter {
                it.name.contains(searchQuery.text, ignoreCase = true)
            }

            items(filteredBosses) { boss ->
                // Mapear las apariciones y dungeons a sus nombres
                val gameNames = boss.appearances.mapNotNull { url ->
                    val gameId = url.split("/").last()
                    gameUrlToNameMap[gameId]
                }
                val dungeonNames = boss.dungeons.mapNotNull { url ->
                    val dungeonId = url.split("/").last()
                    dungeonUrlToNameMap[dungeonId]
                }

                BossItem(boss.copy(appearances = gameNames, dungeons = dungeonNames))
            }
        }
    }

    // Llamadas iniciales para obtener datos
    LaunchedEffect(Unit) {
        viewModel.fetchBosses()
        gameViewModel.fetchGames()
        dungeonViewModel.fetchDungeons()
    }
}

@Composable
fun BossItem(boss: Bosses) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Nombre del boss
            Text(
                text = boss.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Descripción del boss
            Text(
                text = boss.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar juegos donde aparece el boss
            boss.appearances.forEach { gameName ->
                Text(
                    text = "Juego que Aparece: $gameName",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Mostrar dungeons asociados al boss
            boss.dungeons.forEach { dungeonName ->
                Text(
                    text = "Dungeon: $dungeonName",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

