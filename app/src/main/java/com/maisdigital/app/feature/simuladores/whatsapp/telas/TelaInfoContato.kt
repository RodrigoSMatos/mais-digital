package com.maisdigital.app.feature.simuladores.whatsapp.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.domain.tutorial.alvoTutorial

/**
 * Tela de informações do contato (Pedro Borba).
 *
 * Base das aulas de gerenciamento de contato:
 *  - abrir informações do contato
 *  - mídia, links e documentos
 *  - notificações
 *  - bloquear contato
 *
 * Estrutura fiel ao WhatsApp real (adaptada para contato individual):
 *  - Topo: voltar + QR code + 3 pontinhos
 *  - Avatar grande + nome + número
 *  - 4 botões: Mensagem / Ligar / Vídeo / Buscar
 *  - Mídia, links e docs (com miniaturas)
 *  - Notificações
 *  - Visibilidade de mídia
 *  - Criptografia
 *  - Bloquear
 *
 * Alvos:
 *  - "info_avatar"        → avatar grande
 *  - "info_nome"          → nome do contato
 *  - "info_btn_mensagem"  → botão Mensagem
 *  - "info_btn_ligar"     → botão Ligar
 *  - "info_btn_video"     → botão Vídeo
 *  - "info_btn_buscar"    → botão Buscar
 *  - "info_midia"         → seção Mídia, links e docs
 *  - "info_notificacoes"  → item Notificações
 *  - "info_bloquear"      → item Bloquear
 */
private val VerdeWhatsApp = Color(0xFF075E54)
private val VerdePedro = Color(0xFF00897B)
private val CinzaIcone = Color(0xFF667781)

@Composable
fun TelaInfoContato(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Topo: voltar + QR code + 3 pontinhos
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(56.dp)
                .padding(horizontal = Dimensoes.espacoMedio)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .alvoTutorial("info_voltar")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.QrCode,
                contentDescription = "Código QR",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Mais opções",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

        // Avatar grande
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(120.dp)
                .clip(CircleShape)
                .background(VerdePedro)
                .alvoTutorial("info_avatar"),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "P",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

        // Nome
        Text(
            text = "Pedro Borba",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .alvoTutorial("info_nome")
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Número
        Text(
            text = "+55 11 99999-0000",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 4 botões de ação
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensoes.espacoMedio)
        ) {
            BotaoAcao(
                icone = Icons.Filled.Chat,
                texto = "Mensagem",
                modifier = Modifier.alvoTutorial("info_btn_mensagem")
            )
            BotaoAcao(
                icone = Icons.Filled.Call,
                texto = "Ligar",
                modifier = Modifier.alvoTutorial("info_btn_ligar")
            )
            BotaoAcao(
                icone = Icons.Filled.Videocam,
                texto = "Vídeo",
                modifier = Modifier.alvoTutorial("info_btn_video")
            )
            BotaoAcao(
                icone = Icons.Filled.Search,
                texto = "Buscar",
                modifier = Modifier.alvoTutorial("info_btn_buscar")
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divisor()

        // Seção Mídia, links e docs
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensoes.espacoMedio)
                .alvoTutorial("info_midia")
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Mídia, links e docs",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = "17  >",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Miniaturas de mídia (decorativas)
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimensoes.espacoPequeno),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensoes.espacoMedio)
        ) {
            repeat(4) { indice ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            listOf(
                                Color(0xFFB0BEC5),
                                Color(0xFFCFD8DC),
                                Color(0xFF90A4AE),
                                Color(0xFFB0BEC5)
                            )[indice]
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Photo,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))
        Divisor()

        // Gerenciar armazenamento
        ItemInfo(
            icone = Icons.Filled.Folder,
            titulo = "Gerenciar armazenamento",
            subtitulo = "521,3 MB"
        )

        // Notificações (alvo)
        ItemInfo(
            icone = Icons.Filled.Notifications,
            titulo = "Notificações",
            subtitulo = "Todas",
            modifier = Modifier.alvoTutorial("info_notificacoes")
        )

        // Visibilidade de mídia
        ItemInfo(
            icone = Icons.Filled.Photo,
            titulo = "Visibilidade de mídia",
            subtitulo = null
        )

        // Criptografia
        ItemInfo(
            icone = Icons.Filled.Lock,
            titulo = "Criptografia",
            subtitulo = "As mensagens e ligações são protegidas com criptografia de ponta a ponta."
        )

        Divisor()

        // Bloquear (alvo) — texto em laranja (cor de alerta amigável do app)
        ItemInfo(
            icone = Icons.Filled.Block,
            titulo = "Bloquear Pedro Borba",
            subtitulo = null,
            corDestaque = Color(0xFFE8730C),
            modifier = Modifier.alvoTutorial("info_bloquear")
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun BotaoAcao(
    icone: ImageVector,
    texto: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = Dimensoes.espacoPequeno, vertical = Dimensoes.espacoPequeno)
    ) {
        Icon(
            imageVector = icone,
            contentDescription = texto,
            tint = VerdeWhatsApp,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyMedium,
            color = VerdeWhatsApp
        )
    }
}

@Composable
private fun ItemInfo(
    icone: ImageVector,
    titulo: String,
    subtitulo: String?,
    corDestaque: Color? = null,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensoes.espacoMedio, vertical = Dimensoes.espacoMedio)
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = corDestaque ?: CinzaIcone,
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = corDestaque ?: MaterialTheme.colorScheme.onSurface
            )
            if (subtitulo != null) {
                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun Divisor() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(Color(0xFFF0F2F5))
    )
}