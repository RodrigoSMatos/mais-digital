package com.maisdigital.app.feature.tutorial

import android.app.Activity
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
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
    // Esconde a barra de navegação enquanto a tela do tutorial está ativa.
    // O idoso ainda pode trazê-la de volta com swipe a partir da borda inferior.
    // Quando sai da TutorialScreen (concluiu, abandonou), a barra reaparece.
    EsconderBarraNavegacao()

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
    val feedbackRectAtual = state?.let { s ->
        s.aula.passos.getOrNull(s.indicePasso)?.elementoFeedbackId?.let { alvos[it] }
    }
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
                    alvoRect = alvoRectAtual,
                    feedbackRect = feedbackRectAtual
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

            // Overlay transparente que descarta qualquer toque durante o período
            // de carência. Por ser o último filho (maior z-order), é o hit target
            // exclusivo: nenhum irmão recebe o evento enquanto estiver presente.
            if (!aceitaCliques) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
                                while (true) {
                                    awaitPointerEvent(PointerEventPass.Initial)
                                        .changes.forEach { it.consume() }
                                }
                            }
                        }
                )
            }
        }
    }
}
/**
 * Esconde a barra de navegação do Android (botões home/voltar/recentes)
 * enquanto este composable estiver na composição. A barra reaparece com
 * um swipe a partir da borda inferior — comportamento padrão de apps como
 * YouTube em tela cheia.
 *
 * Quando o composable sai da composição (usuário saiu da TutorialScreen),
 * a barra volta automaticamente ao estado normal.
 *
 * IMPORTANTE: não escondemos a status bar (relógio/bateria). Tirar ela
 * pode confundir o idoso achando que perdeu sinal ou bateria.
 */
@Composable
private fun EsconderBarraNavegacao() {
    val view = LocalView.current
    DisposableEffect(view) {
        val window = (view.context as? Activity)?.window
        if (window == null) {
            return@DisposableEffect onDispose { }
        }
        val controller = WindowCompat.getInsetsController(window, view)
        // Esconde apenas a barra de navegação (não a status bar)
        controller.hide(WindowInsetsCompat.Type.navigationBars())
        // Permite reaparecer temporariamente com swipe a partir da borda
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        onDispose {
            // Restaura a barra quando sai do tutorial
            controller.show(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        }
    }
}