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

/**
 * Tela hospedeira do Tutorial Guiado.
 *
 * Estrutura em camadas (de baixo pra cima):
 *  1. Simulador — Modifier.alvoTutorial intercepta cliques nos elementos certos.
 *  2. Camada de "clique fora" — recebe cliques no fundo (NÃO sobre o simulador).
 *  3. Spotlight escurecendo o resto da tela (sem interceptar cliques).
 *  4. Cartão de instrução flutuante.
 *  5. Mensagem de erro amigável.
 *
 * IMPORTANTE: as camadas 3, 4 e 5 NÃO interceptam cliques porque os Composables
 * por padrão só consomem cliques se tiverem Modifier.clickable. O simulador
 * recebe os cliques nos elementos marcados (via Modifier.alvoTutorial).
 *
 * Cliques fora dos alvos: capturados pela camada 2, que envolve o simulador
 * com clickable padrão (sem indication) e despacha "aoClicarFora()".
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

    // InteractionSource compartilhado para o "clickable fora"
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
            // CAMADA 1+2 COMBINADAS: Simulador envolto em clickable.
            // O Modifier.clickable do simulador captura cliques que NÃO
            // foram consumidos pelos elementos alvo internos.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shakeEm(shakeTrigger)
                    .clickable(
                        interactionSource = interactionFora,
                        indication = null
                    ) {
                        viewModel.engine.aoClicarFora()
                    }
            ) {
                SimuladorWhatsApp(appId = appId, aulaId = aulaId)
            }

            // CAMADA 3: Spotlight overlay (NÃO intercepta cliques pois Canvas
            // sem clickable é "transparente" ao toque).
            SpotlightOverlay(alvoRect = alvoRectAtual)

            // CAMADA 4: Cartão de instrução
            state?.let { s ->
                CartaoInstrucao(
                    instrucao = s.instrucaoAtual,
                    progresso = s.progresso,
                    indicePasso = s.indicePasso,
                    totalPassos = s.totalPassos,
                    alvoRect = alvoRectAtual
                )
            }

            // CAMADA 5: Mensagem de erro amigável
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