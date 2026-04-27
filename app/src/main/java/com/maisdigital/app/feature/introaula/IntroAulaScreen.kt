package com.maisdigital.app.feature.introaula

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun IntroAulaScreen(
    appId: String,
    aulaId: String,
    aoVoltar: () -> Unit,
    aoComecar: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Intro — $appId / $aulaId", style = MaterialTheme.typography.headlineMedium)
    }
}