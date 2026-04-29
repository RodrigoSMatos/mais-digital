package com.maisdigital.app.feature.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maisdigital.app.core.ui.components.shakeEm
import com.maisdigital.app.domain.tutorial.LocalRegistroAlvos
import com.maisdigital.app.domain.tutorial.LocalTutorialEngine
import com.maisdigital.app.feature.simuladores.whatsapp.SimuladorWhatsApp
import com.maisdigital.app.feature.tutorial.components.CartaoInstrucao
import com.maisdigital.app.feature.tutorial.components.MensagemErroAmigavel
import com.maisdigital.app.feature.tutorial.components.SpotlightOverlay

/**
 * Tela hospedeira do Tutorial Guiado.
 *
 * Estrutura em camadas (de baixo pra cima):
 *  1. Simulador (ex: SimuladorWhatsApp) — telas com Modifier.alvoTutorial.
 *  2. Captura de cliques fora dos alvos (gera erro amigável).
 *  3. Spotlight escurecendo o resto da tela.
 *  4. Cartão de instrução flutuante.
 *  5. Mensagem de erro amigável (quando há).
 */
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

    // Quando concluir, navega para tela de parabéns
    LaunchedEffect(state?.concluida) {
        if (state?.concluida == true) {
            aoConcluir()
        }
    }

    val alvoRectAtual = state?.elementoAlvoId?.let { alvos[it] }

    CompositionLocalProvider(
        LocalTutorialEngine provides viewModel.engine,
        LocalRegistroAlvos provides viewModel.registro
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Camada 1: Simulador (varia conforme o app)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shakeEm(shakeTrigger)
            ) {
                SimuladorWhatsApp(appId = appId, aulaId = aulaId)
            }

            // Camada 2: Captura cliques fora dos alvos.
            // Importante: vem ANTES do overlay para que cliques nos elementos
            // (que ficam abaixo) ainda passem.
            //
            // Truque: usamos pointerInput com awaitPointerEventScope no detectTapGestures
            // só pra registrar tap genérico. O Modifier.alvoTutorial nos elementos abaixo
            // consome o tap em cima deles ANTES desta camada (graças ao Modifier.clickable lá).
            //
            // Quando o tap chega aqui, é porque NENHUM alvo foi atingido.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(state?.indicePasso) {
                        detectTapGestures(
                            onTap = {
                                viewModel.engine.aoClicarFora()
                            }
                        )
                    }
            )

            // Camada 3: Spotlight overlay
            SpotlightOverlay(alvoRect = alvoRectAtual)

            // Camada 4: Cartão de instrução
            state?.let { s ->
                CartaoInstrucao(
                    instrucao = s.instrucaoAtual,
                    progresso = s.progresso,
                    indicePasso = s.indicePasso,
                    totalPassos = s.totalPassos,
                    alvoRect = alvoRectAtual
                )
            }

            // Camada 5: Mensagem de erro amigável (alinhada ao bottom)
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