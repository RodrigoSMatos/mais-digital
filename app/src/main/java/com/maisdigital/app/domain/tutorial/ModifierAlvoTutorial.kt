package com.maisdigital.app.domain.tutorial

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalInspectionMode
import kotlinx.coroutines.delay

fun Modifier.alvoTutorial(id: String): Modifier = composed {
    val engine = LocalTutorialEngine.current
    val registro = LocalRegistroAlvos.current
    val emPreview = LocalInspectionMode.current

    val interaction = remember { MutableInteractionSource() }

    // Pequeno delay também aqui para descartar tap residual da tela anterior
    var pronto by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(800)
        pronto = true
    }

    DisposableEffect(id, registro) {
        onDispose {
            registro?.desregistrar(id)
        }
    }

    val comClickable = if (engine != null) {
        this.clickable(
            interactionSource = interaction,
            indication = null
        ) {
            if (pronto) {
                engine.aoClicar(id)
            }
        }
    } else {
        this
    }

    if (registro != null && !emPreview) {
        comClickable.onGloballyPositioned { coords ->
            val rect: Rect = coords.boundsInRoot()
            registro.registrar(id, rect)
        }
    } else {
        comClickable
    }
}