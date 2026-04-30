package com.maisdigital.app.feature.simuladores.whatsapp.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppClaro
import com.maisdigital.app.domain.tutorial.alvoTutorial
import com.maisdigital.app.feature.simuladores.whatsapp.components.HeaderWhatsApp
import com.maisdigital.app.feature.simuladores.whatsapp.components.IconeHeader

/**
 * Tela inicial do WhatsApp simulado: lista de conversas.
 *
 * Alvos:
 *  - "btn_novo_contato"  → ícone de adicionar pessoa no header
 *  - "conversa_maria"    → item da Maria
 */
@Composable
fun TelaListaConversas(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeaderWhatsApp(
            titulo = "WhatsApp",
            iconesDireita = {
                IconeHeader(
                    icone = Icons.Filled.PersonAdd,
                    descricao = "Novo contato",
                    modifier = Modifier.alvoTutorial("btn_novo_contato")
                )
            }
        )

        Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))

        ItemConversa(
            inicial = "M",
            corAvatar = Color(0xFFE91E63),
            nome = "Maria",
            ultimaMensagem = "Oi! Tudo bem?",
            horario = "10:42",
            modifier = Modifier.alvoTutorial("conversa_maria")
        )

        ItemConversa(
            inicial = "J",
            corAvatar = Color(0xFF3F51B5),
            nome = "João",
            ultimaMensagem = "Bom dia!",
            horario = "09:15"
        )

        ItemConversa(
            inicial = "A",
            corAvatar = Color(0xFFFF9800),
            nome = "Ana",
            ultimaMensagem = "Te ligo mais tarde 😊",
            horario = "Ontem"
        )

        ItemConversa(
            inicial = "P",
            corAvatar = Color(0xFF4CAF50),
            nome = "Pedro",
            ultimaMensagem = "Obrigado!",
            horario = "Ontem"
        )
    }
}

@Composable
private fun ItemConversa(
    inicial: String,
    corAvatar: Color,
    nome: String,
    ultimaMensagem: String,
    horario: String,
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
                .background(corAvatar),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = inicial,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.size(Dimensoes.espacoMedio))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = nome,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = ultimaMensagem,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = horario,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}