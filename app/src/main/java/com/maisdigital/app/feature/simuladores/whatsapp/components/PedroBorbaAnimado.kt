package com.maisdigital.app.feature.simuladores.whatsapp.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.maisdigital.app.R
import kotlinx.coroutines.delay

/**
 * Pedro Borba — personagem animado da chamada de vídeo.
 *
 * Animação em estilo flipbook, mas com fade suave entre frames
 * pra evitar o efeito de "troca seca" robótica.
 *
 * Loop:
 *  - 2.0s base (olhos abertos, mãos no bolso)
 *  - 0.15s piscando (olhos fechados)
 *  - 2.5s base
 *  - 4× alternância suave entre aceno_1 e aceno_2 (300ms cada)
 *  - 3.0s base
 *  - repete
 */
@Composable
fun PedroBorbaAnimado(
    modifier: Modifier = Modifier
) {
    var frameAtual by remember { mutableIntStateOf(R.drawable.pedro_base) }

    LaunchedEffect(Unit) {
        while (true) {
            // 1. Base (parado)
            frameAtual = R.drawable.pedro_base
            delay(2000)

            // 2. Pisca uma vez
            frameAtual = R.drawable.pedro_olhos_fechados
            delay(150)

            // 3. Volta pra base
            frameAtual = R.drawable.pedro_base
            delay(2500)

            // 4. Acena 4 vezes (alterna entre as duas poses)
            repeat(4) {
                frameAtual = R.drawable.pedro_aceno_1
                delay(300)
                frameAtual = R.drawable.pedro_aceno_2
                delay(300)
            }

            // 5. Volta pra base após acenar
            frameAtual = R.drawable.pedro_base
            delay(3000)
        }
    }

    val context = LocalContext.current

    // AnimatedContent faz fade-in/fade-out entre frames automaticamente,
    // dando fluidez sem precisar de imagens intermediárias.
    AnimatedContent(
        targetState = frameAtual,
        transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 120)) togetherWith
                    fadeOut(animationSpec = tween(durationMillis = 120))
        },
        label = "pedro_flipbook",
        modifier = modifier
    ) { frame ->
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(frame)
                .crossfade(false)
                .build(),
            contentDescription = "Pedro acenando",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}