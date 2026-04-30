package com.maisdigital.app.feature.tutorial.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes

/**
 * Cartão flutuante que mostra a instrução do passo atual.
 *
 * Posicionamento inteligente:
 *  - Por padrão fica na parte superior da tela.
 *  - Se o alvo está na parte superior, o cartão vai para a parte inferior.
 *  - Garante que NUNCA fica em cima do alvo.
 */
@Composable
fun CartaoInstrucao(
    instrucao: String,
    progresso: Float,
    indicePasso: Int,
    totalPassos: Int,
    alvoRect: Rect?,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val density = LocalDensity.current
        val alturaTelaDp = maxHeight
        val alturaTelaPx = with(density) { alturaTelaDp.toPx() }

        // Calcula offset Y: se alvo está na metade de cima da tela,
        // cartão fica embaixo; senão, cartão fica em cima.
        val offsetYDp = with(density) {
            if (alvoRect == null) {
                // Sem alvo: cartão fica na parte superior
                32.dp.toPx().toDp()
            } else {
                val alvoCentroY = alvoRect.center.y
                val ehAlvoNaMetadeDeCima = alvoCentroY < alturaTelaPx / 2f

                if (ehAlvoNaMetadeDeCima) {
                    // Alvo em cima → cartão na parte INFERIOR
                    (alturaTelaPx - 250.dp.toPx() - 32.dp.toPx()).toDp()
                } else {
                    // Alvo embaixo → cartão na parte SUPERIOR
                    32.dp.toPx().toDp()
                }
            }
        }

        val offsetYAnimado by animateDpAsState(
            targetValue = offsetYDp,
            animationSpec = tween(durationMillis = 250),
            label = "offset_cartao"
        )

        Card(
            shape = RoundedCornerShape(Dimensoes.raioCantoGrande),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensoes.espacoMedio)
                .offset(y = offsetYAnimado)
        ) {
            Column(modifier = Modifier.padding(Dimensoes.espacoMedio)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.TouchApp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))
                    Text(
                        text = "Passo ${indicePasso + 1} de $totalPassos",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(Dimensoes.espacoPequeno))

                Text(
                    text = instrucao,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

                LinearProgressIndicator(
                    progress = { progresso },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}