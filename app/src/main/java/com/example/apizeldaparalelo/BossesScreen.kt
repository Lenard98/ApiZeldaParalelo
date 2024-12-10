package com.example.apizeldaparalelo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.apizeldaparalelo.Modelos.Boss
import com.example.apizeldaparalelo.implementacionAPI.BossesViewModel
import com.example.apizeldaparalelo.implementacionAPI.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BossScreen(viewModel: BossesViewModel, gameViewModel: GameViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val bosses by viewModel.bosses.collectAsState(initial = emptyList())
    val errorMessage by viewModel.error.collectAsState()
    val games by gameViewModel.games.collectAsState(initial = emptyList())


    val gameUrlToNameMap = games.associateBy { it.id }.mapValues { it.value.name }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(43, 114, 24))
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar bosses", color = Color.Black) },
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
            val filteredBosses = bosses.filter {
                it.name.contains(searchQuery.text, ignoreCase = true)
            }

            items(filteredBosses) { boss ->

                val gameNames = boss.appearances.mapNotNull { url ->
                    val gameId = url.split("/").last()
                    gameUrlToNameMap[gameId]
                }

                BossItem(boss.copy(appearances = gameNames))
            }
        }
    }


    LaunchedEffect(Unit) {
        viewModel.fetchBosses()
        gameViewModel.fetchGames()
    }
}

@Composable
fun BossItem(boss: Boss) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(196, 175, 109))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = boss.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = boss.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))


            if (boss.gender != null) {
                Text(
                    text = "Género: ${boss.gender}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
            if (boss.race != null) {
                Text(
                    text = "Raza: ${boss.race}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }


            boss.appearances.forEach { gameName ->
                Text(
                    text = "Aparece en: $gameName",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
