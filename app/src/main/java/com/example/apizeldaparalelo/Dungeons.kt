package com.example.apizeldaparalelo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.apizeldaparalelo.Modelos.Dungeon
import com.example.apizeldaparalelo.implementacionAPI.DungeonViewModel
import com.example.apizeldaparalelo.implementacionAPI.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DungeonScreen(viewModel: DungeonViewModel, gameViewModel: GameViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val dungeons by viewModel.dungeons.collectAsState(initial = emptyList())
    val errorMessage by viewModel.error.collectAsState()
    val games by gameViewModel.games.collectAsState(initial = emptyList())

    val gameUrlToNameMap = games.associateBy { it.id }.mapValues { it.value.name }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(43, 114, 24)) // Mismo color de fondo
            .padding(16.dp)
    ) {
        // Barra de búsqueda con fondo blanco
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar dungeons", color = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                content = { Text(text = it, color = Color.Black) },
                containerColor = Color.Yellow
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val filteredDungeons = dungeons.filter {
                it.name.contains(searchQuery.text, ignoreCase = true)
            }

            items(filteredDungeons) { dungeon ->
                val gameNames = dungeon.appearances.mapNotNull { url ->
                    val gameId = url.split("/").last()
                    gameUrlToNameMap[gameId]
                }

                DungeonItem(dungeon.copy(appearances = gameNames))
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchDungeons()
        gameViewModel.fetchGames()
    }
}

@Composable
fun DungeonItem(dungeon: Dungeon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(196, 175, 109))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = dungeon.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                overflow = TextOverflow.Visible
            )
            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = dungeon.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                overflow = TextOverflow.Visible
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (dungeon.appearances.isNotEmpty()) {
                Text(
                    text = "Aparece en:",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                dungeon.appearances.forEach { gameName ->
                    Text(
                        text = "- $gameName",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
            } else {
                Text(
                    text = "No tiene juegos asociados",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
        }
    }
}
