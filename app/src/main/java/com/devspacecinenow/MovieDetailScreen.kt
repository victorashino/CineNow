package com.devspacecinenow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MovieDetailScreen() {
    MovieDetailContent()
}

@Composable
private fun MovieDetailContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Movie Detail Screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailScreenPreview() {
    MovieDetailScreen()
}