package com.maisdigital.app.feature.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maisdigital.app.core.ui.components.shakeEm
import com.maisdigital.app.domain.tutorial.LocalRegistroAlvos
import com.maisdigital.app.domain.tutorial.LocalTutorialEngine
import com.maisdigital.app.feature.simuladores.whatsapp.SimuladorWhatsApp
import com.maisdigital.app.feature.tutorial.components.CartaoInstrucao
import com.maisdigital.app.feature.tutorial.components.MensagemErroAmigavel
import com.maisdigital.app.feature.tutorial.components.SpotlightOverlay

@Composable
fun TutorialScreen(
    appId: String,
    aulaId: String,
    aoSair: () -> Unit,
    aoConcluir: () -> Unit,
    viewModel: TutorialViewModel = viewModel()
) {
    LaunchedEffect(appId, aulaId) {
        viewModel.carregarAula(appId, aulaId)
    }

    val state by viewModel.engine.state.collectAsState()
    val alvos by viewModel.registro.alvos.collectAsState()
    val shakeTrigger by viewModel.shakeTrigger.collectAsState()
    val aceitaCliques by viewModel.aceitaCliques.collectAsState()

    LaunchedEffect(state?.concluida) {
        if (state?.concluida == true) {
            aoConcluir()
        }
    }

    val alvoRectAtual = state?.elementoAlvoId?.let { alvos[it] }
    val interactionFora = remember { MutableInteractionSource() }

    CompositionLocalProvider(
        LocalTutorialEngine provides viewModel.engine,
        LocalRegistroAlvos provides viewModel.registro
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Camada do simulador.
            // O clickable só é aplicado APÓS o período de carência.
            // Antes disso, o simulador ignora completamente os toques.
            val modSimulador = if (aceitaCliques) {
                Modifier
                    .fillMaxSize()
                    .shakeEm(shakeTrigger)
                    .clickable(
                        interactionSource = interactionFora,
                        indication = null
                    ) {
                        viewModel.engine.aoClicarFora()
                    }
            } else {
                Modifier
                    .fillMaxSize()
                    .shakeEm(shakeTrigger)
            }

            Box(modifier = modSimulador) {
                SimuladorWhatsApp(appId = appId, aulaId = aulaId)
            }

            SpotlightOverlay(alvoRect = alvoRectAtual)

            state?.let { s ->
                CartaoInstrucao(
                    instrucao = s.instrucaoAtual,
                    progresso = s.progresso,
                    indicePasso = s.indicePasso,
                    totalPassos = s.totalPassos,
                    alvoRect = alvoRectAtual
                )
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
            ) {
                MensagemErroAmigavel(mensagem = state?.erro)
            }
        }
    }
}