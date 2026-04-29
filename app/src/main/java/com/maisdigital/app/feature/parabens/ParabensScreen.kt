package com.maisdigital.app.feature.parabens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.maisdigital.app.R
import com.maisdigital.app.core.ui.components.BotaoGrande
import com.maisdigital.app.core.ui.components.BotaoGrandeSecundario
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.core.ui.theme.VerdeAcao

/**
 * Tela exibida ao concluir uma aula.
 * Tem três opções: refazer, voltar para lista de aulas, ou voltar ao menu.
 */
@Composable
fun ParabensScreen(
    appId: String,
    aulaId: String,
    aoRepetir: () -> Unit,
    aoVoltarLista: () -> Unit,
    aoVoltarMenu: () -> Unit
) {
    val escala = remember { Animatable(0.5f) }

    LaunchedEffect(Unit) {
        escala.animateTo(1f, animationSpec = tween(500))
    }

    Scaffold { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimensoes.espacoGrande)
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .scale(escala.value),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Celebration,
                    contentDescription = null,
                    tint = VerdeAcao,
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(Dimensoes.espacoGrande))

            Text(
                text = stringResource(R.string.parabens_titulo),
                style = MaterialTheme.typography.displayLarge,
                color = VerdeAcao,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

            Text(
                text = stringResource(R.string.parabens_mensagem),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Dimensoes.espacoExtraGrande))

            BotaoGrande(
                texto = stringResource(R.string.botao_voltar_aulas),
                aoClicar = aoVoltarLista
            )

            Spacer(modifier = Modifier.height(Dimensoes.espacoPequeno))

            BotaoGrandeSecundario(
                texto = stringResource(R.string.botao_repetir),
                aoClicar = aoRepetir,
                icone = Icons.Filled.Refresh
            )

            Spacer(modifier = Modifier.height(Dimensoes.espacoPequeno))

            BotaoGrandeSecundario(
                texto = "Voltar ao início",
                aoClicar = aoVoltarMenu,
                icone = Icons.Filled.Home
            )
        }
    }
}