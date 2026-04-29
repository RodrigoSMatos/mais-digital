package com.maisdigital.app.domain.tutorial

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalInspectionMode

/**
 * Marca um elemento como "alvo" do sistema de tutorial guiado.
 *
 * Faz três coisas:
 *  1. Registra a posição do elemento no RegistroAlvos.
 *  2. Captura cliques e despacha pro TutorialEngine.
 *  3. Se desregistra automaticamente quando o Composable sai da tela.
 *
 * Em modo livre (engine == null), o modifier vira praticamente um no-op,
 * permitindo que as mesmas telas do simulador funcionem fora de tutoriais.
 */
fun Modifier.alvoTutorial(id: String): Modifier = composed {
    val engine = LocalTutorialEngine.current
    val registro = LocalRegistroAlvos.current
    val emPreview = LocalInspectionMode.current

    val interaction = remember { MutableInteractionSource() }

    // Auto-desregistro quando o elemento sai da composição
    DisposableEffect(id, registro) {
        onDispose {
            registro?.desregistrar(id)
        }
    }

    val base = if (engine != null) {
        this.clickable(
            interactionSource = interaction,
            indication = null
        ) {
            engine.aoClicar(id)
        }
    } else {
        // Sem engine = modo livre. Sem clique automático.
        this
    }

    // Reporta posição se houver registro ativo (e não estamos em preview).
    if (registro != null && !emPreview) {
        base.onGloballyPositioned { coords ->
            val rect: Rect = coords.boundsInRoot()
            registro.registrar(id, rect)
        }
    } else {
        base
    }
}