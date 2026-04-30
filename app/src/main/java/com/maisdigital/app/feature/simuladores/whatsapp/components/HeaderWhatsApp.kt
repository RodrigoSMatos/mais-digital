package com.maisdigital.app.feature.simuladores.whatsapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppHeader

/**
 * Header verde reutilizável.
 * Pode ter ícones à direita (cada um com seu Modifier para virar alvo).
 */
@Composable
fun HeaderWhatsApp(
    titulo: String,
    modifier: Modifier = Modifier,
    iconesDireita: @Composable () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(VerdeWhatsAppHeader)
            .padding(horizontal = Dimensoes.espacoMedio)
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        iconesDireita()
    }
}

/**
 * Botão de ícone branco redondinho no header.
 */
@Composable
fun IconeHeader(
    icone: ImageVector,
    descricao: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(48.dp)
    ) {
        Icon(
            imageVector = icone,
            contentDescription = descricao,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
    }
}