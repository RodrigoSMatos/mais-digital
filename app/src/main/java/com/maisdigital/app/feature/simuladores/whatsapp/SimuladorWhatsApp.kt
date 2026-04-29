package com.maisdigital.app.feature.simuladores.whatsapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppHeader
import com.maisdigital.app.domain.tutorial.alvoTutorial

/**
 * Placeholder mínimo do simulador WhatsApp.
 *
 * Para a Etapa 4 já funcionar, exponho um item de conversa "Maria"
 * que responde ao alvo "conversa_maria".
 * A versão real virá na Etapa 5.
 */
@Composable
fun SimuladorWhatsApp(
    appId: String,
    aulaId: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header verde do WhatsApp
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(VerdeWhatsAppHeader)
                .padding(horizontal = Dimensoes.espacoMedio),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "WhatsApp",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

        // Item de conversa "Maria" (alvo: conversa_maria)
        ItemConversa(
            nome = "Maria",
            ultimaMensagem = "Oi! Tudo bem?",
            modifier = Modifier.alvoTutorial("conversa_maria")
        )

        // Item de conversa "João" (não é alvo - clicar aqui gera erro)
        ItemConversa(
            nome = "João",
            ultimaMensagem = "Bom dia!",
            modifier = Modifier
        )
    }
}

@Composable
private fun ItemConversa(
    nome: String,
    ultimaMensagem: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensoes.espacoMedio,
                vertical = Dimensoes.espacoMedio
            )
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFFB0BEC5)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nome.first().toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.size(Dimensoes.espacoMedio))
        Column {
            Text(
                text = nome,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = ultimaMensagem,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}