package com.maisdigital.app.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.maisdigital.app.core.ui.theme.Dimensoes

@Composable
fun BotaoSecundario(
    texto: String,
    aoClicar: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = aoClicar,
        shape = RoundedCornerShape(Dimensoes.raioCantoGrande),
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensoes.alturaBotaoSecundario)
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}