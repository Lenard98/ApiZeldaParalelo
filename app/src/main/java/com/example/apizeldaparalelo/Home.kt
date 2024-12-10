package com.example.apizeldaparalelo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0, 0, 0))
    ) {
        // Imagen de fondo
        AsyncImage(
            model = "https://wallpapers.com/images/featured-full/triforce-got6xicxxskxgye3.jpg",
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.Center,
        )

        // Texto "APIZELDA"
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        ) {
            Text(
                text = "APIZELDA",
                style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Texto "®ADFLL" en la parte inferior
        Text(
            text = "®ADFLL",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}
