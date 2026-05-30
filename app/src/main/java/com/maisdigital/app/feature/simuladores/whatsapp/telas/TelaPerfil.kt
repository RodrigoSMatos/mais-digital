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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
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
 * Tela de Perfil do próprio usuário.
 *
 * Usada na aula "Ver seu próprio número de telefone". Mostra o avatar,
 * nome, recado, telefone e links — fiel ao WhatsApp real, mas com dados
 * genéricos ("Seu nome") para não confundir o idoso.
 *
 * Alvos:
 *  - "perfil_voltar"   → seta de voltar
 *  - "perfil_telefone" → linha do telefone (objetivo da aula 7)
 */
private val VerdeWhatsApp = Color(0xFF075E54)
private val CinzaAvatarVazio = Color(0xFFE3E8EE)
private val CinzaIcone = Color(0xFF667781)

@Composable
fun TelaPerfil(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Topo: voltar + título "Perfil"
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
                    .alvoTutorial("perfil_voltar")
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
                text = "Perfil",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Avatar grande centralizado
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(140.dp)
                .clip(CircleShape)
                .background(CinzaAvatarVazio),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Foto de perfil",
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(Dimensoes.espacoPequeno))

        // "Editar" abaixo do avatar (em verde, decorativo)
        Text(
            text = "Editar",
            style = MaterialTheme.typography.bodyLarge,
            color = VerdeWhatsApp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = Dimensoes.espacoPequeno)
        )

        Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

        // Nome
        ItemPerfil(
            icone = Icons.Filled.Person,
            titulo = "Nome",
            valor = "Seu nome"
        )

        // Recado
        ItemPerfil(
            icone = Icons.Filled.Info,
            titulo = "Recado",
            valor = "Definir Recado",
            valorEmDestaque = true
        )

        // Telefone (ALVO DA AULA 7)
        ItemPerfil(
            icone = Icons.Filled.Call,
            titulo = "Telefone",
            valor = "+55 11 95446-0000",
            modifier = Modifier.alvoTutorial("perfil_telefone")
        )

        // Links
        ItemPerfil(
            icone = Icons.Filled.Link,
            titulo = "Links",
            valor = "Adicionar links",
            valorEmDestaque = true
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ItemPerfil(
    icone: ImageVector,
    titulo: String,
    valor: String,
    valorEmDestaque: Boolean = false,
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
                text = valor,
                style = MaterialTheme.typography.bodyMedium,
                color = if (valorEmDestaque) VerdeWhatsApp
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}