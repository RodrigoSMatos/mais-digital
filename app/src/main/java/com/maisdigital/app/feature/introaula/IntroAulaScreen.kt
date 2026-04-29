package com.maisdigital.app.feature.introaula

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.maisdigital.app.R
import com.maisdigital.app.core.ui.components.BarraTopo
import com.maisdigital.app.core.ui.components.BotaoGrande
import com.maisdigital.app.core.ui.theme.AzulSuave
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.data.catalog.CatalogoApps
import com.maisdigital.app.data.catalog.CatalogoAulasWhatsApp

/**
 * Introdução de uma aula — mostra o que o usuário vai aprender
 * e oferece um botão grande para iniciar o tutorial guiado.
 */
@Composable
fun IntroAulaScreen(
    appId: String,
    aulaId: String,
    aoVoltar: () -> Unit,
    aoComecar: () -> Unit
) {
    val aula = when (appId) {
        CatalogoApps.ID_WHATSAPP -> CatalogoAulasWhatsApp.buscarPorId(aulaId)
        else -> null
    }

    Scaffold(
        topBar = {
            BarraTopo(
                titulo = "Aula ${aula?.ordem ?: ""}",
                aoVoltar = aoVoltar
            )
        }
    ) { padding ->
        if (aula == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Aula não encontrada.")
            }
            return@Scaffold
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimensoes.espacoGrande)
        ) {
            Spacer(modifier = Modifier.height(Dimensoes.espacoExtraGrande))

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .padding(0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensoes.espacoGrande))

            Text(
                text = aula.titulo,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

            Text(
                text = aula.descricao,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            BotaoGrande(
                texto = stringResource(R.string.botao_comecar),
                aoClicar = aoComecar,
                icone = Icons.Filled.PlayArrow
            )

            Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))
        }
    }
}