package com.maisdigital.app.feature.simuladores.whatsapp.telas

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppFAB
import com.maisdigital.app.domain.tutorial.alvoTutorial
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding

/**
 * Tela de Contatos do WhatsApp simulado.
 *
 * Igual ao real:
 *  - Header com seta de voltar + "Contatos" + contagem + busca
 *  - Item "Novo grupo"
 *  - Item "Novo contato" (alvo da Aula 1)
 *  - Item "Nova comunidade"
 *  - Seção "Contatos no WhatsApp" com lista
 *
 * Alvo:
 *  - "btn_novo_contato" → item "Novo contato"
 */
@Composable
fun TelaContatos(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header branco
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .windowInsetsPaddingStatusBarsContatos()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = Dimensoes.espacoMedio)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Contatos",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "144 contatos",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Pesquisar",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimensoes.espacoPequeno))

        // Item "Novo grupo"
        ItemAcaoContato(
            icone = Icons.Filled.Groups,
            titulo = "Novo grupo"
        )

        // Item "Novo contato" (alvo)
        ItemAcaoContato(
            icone = Icons.Filled.PersonAdd,
            titulo = "Novo contato",
            modifier = Modifier.alvoTutorial("btn_novo_contato")
        )

        // Item "Nova comunidade"
        ItemAcaoContato(
            icone = Icons.Filled.PersonAddAlt,
            titulo = "Nova comunidade"
        )

        Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

        // Cabeçalho da seção
        Text(
            text = "Contatos no WhatsApp",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(
                horizontal = Dimensoes.espacoMedio,
                vertical = Dimensoes.espacoPequeno
            )
        )

        // Alguns contatos fake
        ItemContato(inicial = "A", corAvatar = Color(0xFF4DB6AC), nome = "Alan", status = "Disponível")
        ItemContato(inicial = "A", corAvatar = Color(0xFFFFB74D), nome = "Alice", status = "Disponível")
        ItemContato(inicial = "A", corAvatar = Color(0xFFE57373), nome = "Aline", status = "Só chamadas urgentes")
        ItemContato(inicial = "A", corAvatar = Color(0xFF81C784), nome = "Amanda", status = "Disponível")
    }
}

@Composable
private fun ItemAcaoContato(
    icone: ImageVector,
    titulo: String,
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
                .size(48.dp)
                .clip(CircleShape)
                .background(VerdeWhatsAppFAB),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icone,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ItemContato(
    inicial: String,
    corAvatar: Color,
    nome: String,
    status: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensoes.espacoMedio,
                vertical = Dimensoes.espacoPequeno
            )
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(corAvatar),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = inicial,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
        Column {
            Text(
                text = nome,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = status,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun Modifier.windowInsetsPaddingStatusBarsContatos(): Modifier =
    this.windowInsetsPadding(WindowInsets.statusBars)