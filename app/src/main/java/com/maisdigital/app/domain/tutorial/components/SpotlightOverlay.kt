package com.maisdigital.app.domain.tutorial.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.unit.dp

/**
 * Camada que escurece a tela inteira e abre um "buraco" ao redor
 * do elemento alvo, com pulsação suave para chamar atenção.
 *
 * [alvoRect] é a posição do elemento em coordenadas raiz (mesma referência
 * usada por boundsInRoot()).
 */
@Composable
fun SpotlightOverlay(
    alvoRect: Rect?,
    modifier: Modifier = Modifier,
    corOverlay: Color = Color.Black.copy(alpha = 0.65f),
    corBorda: Color = Color(0xFFFFC107) // AmareloDestaque
) {
    // Pulsação suave da borda (1.0 -> 1.08 -> 1.0)
    val pulso by animateFloatAsState(
        targetValue = if (alvoRect != null) 1f else 0f,
        animationSpec = tween(durationMillis = 250),
        label = "pulso_alvo"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        if (alvoRect == null) {
            // Sem alvo definido: escurece tudo.
            drawIntoCanvas { canvas ->
                val paint = Paint().apply { color = corOverlay }
                canvas.drawRect(
                    Rect(Offset.Zero, Size(size.width, size.height)),
                    paint
                )
            }
            return@Canvas
        }

        // Padding para o "buraco" não ficar grudado no elemento
        val padding = 12.dp.toPx()
        val areaIluminada = Rect(
            left = alvoRect.left - padding,
            top = alvoRect.top - padding,
            right = alvoRect.right + padding,
            bottom = alvoRect.bottom + padding
        )

        // Desenha overlay escuro com "buraco" via BlendMode.Clear
        drawIntoCanvas { canvas ->
            val paintEscuro = Paint().apply { color = corOverlay }
            val paintBuraco = Paint().apply {
                color = Color.Transparent
                blendMode = BlendMode.Clear
            }

            canvas.saveLayer(
                Rect(Offset.Zero, Size(size.width, size.height)),
                Paint()
            )
            canvas.drawRect(
                Rect(Offset.Zero, Size(size.width, size.height)),
                paintEscuro
            )
            canvas.drawRoundRect(
                left = areaIluminada.left,
                top = areaIluminada.top,
                right = areaIluminada.right,
                bottom = areaIluminada.bottom,
                radiusX = 16.dp.toPx(),
                radiusY = 16.dp.toPx(),
                paint = paintBuraco
            )
            canvas.restore()
        }

        // Borda destacada (com pulsação) ao redor do alvo
        if (pulso > 0f) {
            val expansao = 4.dp.toPx() * pulso
            drawIntoCanvas { canvas ->
                val paintBorda = Paint().apply {
                    color = corBorda
                    style = PaintingStyle.Stroke
                    strokeWidth = 4.dp.toPx()
                }
                canvas.drawRoundRect(
                    left = areaIluminada.left - expansao,
                    top = areaIluminada.top - expansao,
                    right = areaIluminada.right + expansao,
                    bottom = areaIluminada.bottom + expansao,
                    radiusX = 18.dp.toPx(),
                    radiusY = 18.dp.toPx(),
                    paint = paintBorda
                )
            }
        }
    }
}