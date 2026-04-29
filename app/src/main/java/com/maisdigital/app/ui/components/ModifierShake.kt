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
 * Curto, sutil — só pra dar feedback de "tente de novo".
 */
fun Modifier.shakeEm(trigger: Any?): Modifier = composed {
    val deslocamento = remember { Animatable(0f) }

    LaunchedEffect(trigger) {
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