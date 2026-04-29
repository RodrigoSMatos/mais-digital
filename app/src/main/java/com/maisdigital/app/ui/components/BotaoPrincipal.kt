package com.maisdigital.app.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes

/**
 * Botão principal do +Digital.
 * Grande, texto legível, área de toque generosa para idosos.
 */
@Composable
fun BotaoPrincipal(
    texto: String,
    aoClicar: () -> Unit,
    modifier: Modifier = Modifier,
    habilitado: Boolean = true
) {
    Button(
        onClick = aoClicar,
        enabled = habilitado,
        shape = RoundedCornerShape(Dimensoes.raioCantoGrande),
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensoes.alturaBotaoPrincipal)
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelLarge
        )
    }
}