package com.maisdigital.app.core.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Aplica um efeito de tremor horizontal sempre que [trigger] muda.
 *
 * IMPORTANTE: ignora a primeira composição — o shake só dispara quando
 * o trigger MUDA, não quando ele aparece pela primeira vez. Sem isso,
 * a tela tremeria toda vez que o tutorial abre, pois LaunchedEffect roda
 * uma vez na composição inicial.
 */
fun Modifier.shakeEm(trigger: Any?): Modifier = composed {
    val deslocamento = remember { Animatable(0f) }
    val triggerInicial = remember { trigger }

    LaunchedEffect(trigger) {
        // Pula a primeira execução (composição inicial)
        if (trigger == triggerInicial) return@LaunchedEffect
        if (trigger == null) return@LaunchedEffect

        val sequencia = listOf(0f, -16f, 16f, -12f, 12f, -8f, 8f, 0f)
        sequencia.forEach { destino ->
            deslocamento.animateTo(
                targetValue = destino,
                animationSpec = tween(durationMillis = 50)
            )
        }
    }

    this.graphicsLayer { translationX = deslocamento.value }
}