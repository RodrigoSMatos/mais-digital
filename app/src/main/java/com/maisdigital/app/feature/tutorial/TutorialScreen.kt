package com.maisdigital.app.feature.tutorial

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TutorialScreen(
    appId: String,
    aulaId: String,
    aoSair: () -> Unit,
    aoConcluir: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Tutorial — $appId / $aulaId", style = MaterialTheme.typography.headlineMedium)
    }
}