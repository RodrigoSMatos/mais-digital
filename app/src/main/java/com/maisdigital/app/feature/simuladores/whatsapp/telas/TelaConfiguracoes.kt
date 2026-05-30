package com.maisdigital.app.feature.simuladores.whatsapp.telas

import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VpnKey
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
import com.maisdigital.app.domain.tutorial.alvoTutorial

/**
 * Tela de Configurações do WhatsApp simulado.
 *
 * Usada na aula "Ver seu próprio número de telefone". Mostra um card do
 * próprio perfil no topo (que é o alvo da aula — leva à TelaPerfil) e uma
 * lista de 9 itens de configuração, todos decorativos no MVP.
 *
 * Alvos:
 *  - "config_voltar"      → seta de voltar
 *  - "config_card_perfil" → card do perfil no topo (objetivo da aula)
 */
private val VerdeWhatsApp = Color(0xFF075E54)
private val VerdeWhatsAppFAB = Color(0xFF25D366)
private val CinzaAvatarVazio = Color(0xFFE3E8EE)
private val CinzaIcone = Color(0xFF667781)
private val FundoCardPerfil = Color(0xFFF0F2F5)

@Composable
fun TelaConfiguracoes(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Topo: voltar + título + busca
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
                    .size(48.dp)
                    .alvoTutorial("config_voltar")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
            Text(
                text = "Configurações",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Pesquisar",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(26.dp)
            )
        }

        // Card do perfil (alvo da aula)
        CardPerfil(
            modifier = Modifier.alvoTutorial("config_card_perfil")
        )

        Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

        // Lista de itens (todos decorativos)
        ItemConfig(
            icone = Icons.Filled.VpnKey,
            titulo = "Conta",
            subtitulo = "Notificações de segurança, mudança de número"
        )
        ItemConfig(
            icone = Icons.Filled.Lock,
            titulo = "Privacidade",
            subtitulo = "Contas bloqueadas, mensagens temporárias"
        )
        ItemConfig(
            icone = Icons.Filled.People,
            titulo = "Listas",
            subtitulo = "Gerencie pessoas e grupos"
        )
        ItemConfig(
            icone = Icons.Filled.Chat,
            titulo = "Conversas",
            subtitulo = "Tema, papel de parede, histórico de conversas"
        )
        ItemConfig(
            icone = Icons.Filled.Forum,
            titulo = "Listas de transmissão",
            subtitulo = "Gerencie listas e envie transmissões."
        )
        ItemConfig(
            icone = Icons.Filled.Notifications,
            titulo = "Notificações",
            subtitulo = "Mensagens, grupos, ligações"
        )
        ItemConfig(
            icone = Icons.Filled.Cloud,
            titulo = "Armazenamento e dados",
            subtitulo = "Uso de rede, download automático"
        )
        ItemConfig(
            icone = Icons.Filled.Accessibility,
            titulo = "Acessibilidade",
            subtitulo = "Aumentar o contraste, animação"
        )
        ItemConfig(
            icone = Icons.Filled.Language,
            titulo = "Idioma do app",
            subtitulo = "Português (Brasil)"
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun CardPerfil(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(FundoCardPerfil)
            .padding(horizontal = Dimensoes.espacoMedio, vertical = Dimensoes.espacoMedio)
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(CinzaAvatarVazio),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
        // Nome
        Text(
            text = "Seu nome",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        // QR code
        Icon(
            imageVector = Icons.Filled.QrCode,
            contentDescription = "Código QR",
            tint = VerdeWhatsApp,
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
        // Botão + (decorativo)
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = VerdeWhatsAppFAB,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
private fun ItemConfig(
    icone: ImageVector,
    titulo: String,
    subtitulo: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensoes.espacoMedio, vertical = Dimensoes.espacoMedio)
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = CinzaIcone,
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitulo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}