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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Update
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
import com.maisdigital.app.core.ui.theme.CinzaDivisor
import com.maisdigital.app.core.ui.theme.CinzaIconePequeno
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.core.ui.theme.FundoBuscaWhatsApp
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppClaro
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppFAB
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppHeader
import com.maisdigital.app.domain.tutorial.alvoTutorial
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding


/**
 * Tela inicial do WhatsApp simulado: lista de conversas.
 *
 * Estrutura fiel ao WhatsApp real:
 *  - Header com título "WhatsApp" + ícones de câmera e menu
 *  - Barra de busca "Pergunte à Meta AI ou pesquise"
 *  - Filtros (Todas, Não lidas, Favoritos, Grupos)
 *  - Lista de conversas
 *  - FAB verde flutuante no canto inferior direito (alvo: btn_nova_conversa)
 *  - Barra inferior de navegação (Conversas, Atualizações, Comunidades, Ligações)
 *
 * Alvos:
 *  - "btn_nova_conversa" → FAB verde flutuante (abre tela de Contatos)
 *  - "conversa_maria"    → item da Maria
 */
@Composable
fun TelaListaConversas(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header verde com aplicação de status bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VerdeWhatsAppHeader)
                    .windowInsetsPaddingStatusBars()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = Dimensoes.espacoMedio)
                ) {
                    Text(
                        text = "WhatsApp",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Filled.PhotoCamera,
                        contentDescription = "Câmera",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            // Barra de busca cinza
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimensoes.espacoMedio,
                        vertical = Dimensoes.espacoPequeno
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(FundoBuscaWhatsApp)
                        .padding(horizontal = Dimensoes.espacoMedio)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = CinzaIconePequeno,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(Dimensoes.espacoPequeno))
                    Text(
                        text = "Pesquise",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CinzaIconePequeno
                    )
                }
            }

            // Filtros (chips)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimensoes.espacoMedio)
            ) {
                ChipFiltro(texto = "Todas", selecionado = true)
                Spacer(modifier = Modifier.width(8.dp))
                ChipFiltro(texto = "Não lidas")
                Spacer(modifier = Modifier.width(8.dp))
                ChipFiltro(texto = "Grupos")
            }

            Spacer(modifier = Modifier.height(Dimensoes.espacoPequeno))

            // Lista de conversas
            Column(modifier = Modifier.weight(1f)) {
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

            // Barra inferior de navegação
            BarraInferior()
        }

        // FAB verde flutuante (canto inferior direito)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = Dimensoes.espacoMedio, bottom = 80.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(VerdeWhatsAppFAB)
                .alvoTutorial("btn_nova_conversa")
        ) {
            Icon(
                imageVector = Icons.Filled.AddComment,
                contentDescription = "Nova conversa",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun ChipFiltro(texto: String, selecionado: Boolean = false) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (selecionado) Color(0xFFD9FDD3) else FundoBuscaWhatsApp)
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyMedium,
            color = if (selecionado) Color(0xFF075E54) else MaterialTheme.colorScheme.onSurface
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
                vertical = Dimensoes.espacoPequeno
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

@Composable
private fun BarraInferior() {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White)
            .padding(top = 8.dp)
    ) {
        AbaInferior(icone = Icons.Filled.Chat, texto = "Conversas", selecionado = true)
        AbaInferior(icone = Icons.Filled.Update, texto = "Atualizações")
        AbaInferior(icone = Icons.Filled.Call, texto = "Ligações")
    }
}

@Composable
private fun AbaInferior(icone: ImageVector, texto: String, selecionado: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Pílula com fundo verde claro APENAS na aba selecionada
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(28.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(if (selecionado) Color(0xFFD9FDD3) else Color.Transparent)
                .padding(horizontal = 18.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = icone,
                contentDescription = texto,
                tint = if (selecionado) Color(0xFF075E54) else CinzaIconePequeno,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = texto,
            style = MaterialTheme.typography.bodySmall,
            color = if (selecionado) Color(0xFF075E54) else CinzaIconePequeno
        )
    }
}

/**
 * Helper para aplicar windowInsets sem importar tudo no Composable.
 */
@Composable
private fun Modifier.windowInsetsPaddingStatusBars(): Modifier =
    this.windowInsetsPadding(WindowInsets.statusBars)