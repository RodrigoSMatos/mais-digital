package com.maisdigital.app.domain.tutorial

import androidx.compose.runtime.compositionLocalOf

/**
 * CompositionLocals que permitem que qualquer Composable filho
 * (mesmo dentro do simulador WhatsApp) acesse o engine e o registro.
 *
 * Definidos como nullable para que telas FORA do tutorial
 * (ex: futuro modo livre) consigam reusar os mesmos Composables
 * sem quebrar — basta o Modifier.alvoTutorial virar um no-op.
 */

val LocalTutorialEngine = compositionLocalOf<TutorialEngine?> { null }

val LocalRegistroAlvos = compositionLocalOf<RegistroAlvos?> { null }