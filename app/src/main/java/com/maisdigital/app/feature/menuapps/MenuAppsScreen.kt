package com.maisdigital.app.feature.menuapps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Placeholder da Etapa 1. Versão real na Etapa 3.
 */
@Composable
fun MenuAppsScreen(
    aoSelecionarApp: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Text(
            text = "Menu de Apps (placeholder — Etapa 3)",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}