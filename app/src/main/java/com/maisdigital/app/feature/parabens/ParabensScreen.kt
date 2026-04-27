package com.maisdigital.app.feature.parabens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ParabensScreen(
    appId: String,
    aulaId: String,
    aoRepetir: () -> Unit,
    aoVoltarLista: () -> Unit,
    aoVoltarMenu: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Parabéns — $appId / $aulaId", style = MaterialTheme.typography.headlineMedium)
    }
}