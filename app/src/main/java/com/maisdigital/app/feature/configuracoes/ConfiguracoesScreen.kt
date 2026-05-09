package com.maisdigital.app.feature.configuracoes

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maisdigital.app.core.ui.components.BarraTopo
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.domain.model.TamanhoTexto

@Composable
fun ConfiguracoesScreen(
    aoVoltar: () -> Unit,
    viewModel: ConfiguracoesViewModel = viewModel()
) {
    val tamanhoAtual by viewModel.tamanhoTexto.collectAsState()

    Scaffold(
        topBar = {
            BarraTopo(titulo = "Configurações", aoVoltar = aoVoltar)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimensoes.espacoMedio),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Tamanho do texto",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(Dimensoes.espacoPequeno))
            Text(
                text = "Escolha o tamanho que você consegue ler com mais conforto.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

            TamanhoTexto.values().forEach { tamanho ->
                CardOpcaoTamanho(
                    tamanho = tamanho,
                    selecionado = tamanho == tamanhoAtual,
                    aoSelecionar = { viewModel.setTamanhoTexto(tamanho) }
                )
                Spacer(modifier = Modifier.height(Dimensoes.espacoPequeno))
            }

            Spacer(modifier = Modifier.height(Dimensoes.espacoGrande))

            Text(
                text = "Sobre o +Digital",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(Dimensoes.espacoPequeno))
            Text(
                text = "O +Digital é um aplicativo educativo. Os módulos como WhatsApp são simulações para você praticar com segurança, sem enviar mensagens reais.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CardOpcaoTamanho(
    tamanho: TamanhoTexto,
    selecionado: Boolean,
    aoSelecionar: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(Dimensoes.raioCantoMedio),
        colors = CardDefaults.cardColors(
            containerColor = if (selecionado) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimensoes.elevacaoCard),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { aoSelecionar() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensoes.espacoMedio)
                .height(64.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (selecionado) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (selecionado) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(Dimensoes.espacoMedio))
            Text(
                text = tamanho.nomeExibicao,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}