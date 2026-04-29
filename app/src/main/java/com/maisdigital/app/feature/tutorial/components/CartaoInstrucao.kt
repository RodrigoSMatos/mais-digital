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
 *  - Por padrão fica abaixo do elemento alvo.
 *  - Se não houver espaço embaixo, fica acima.
 *  - Se não houver alvo (transição), fica centralizado.
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
        val alturaCartaoEstimadaPx = with(density) { 180.dp.toPx() }

        // Calcula offset Y do cartão
        val offsetYDp = with(density) {
            if (alvoRect == null) {
                // Centralizado verticalmente
                (alturaTelaDp.toPx() / 2f - alturaCartaoEstimadaPx / 2f).toDp()
            } else {
                val alvoCentroY = alvoRect.center.y
                val telaPx = alturaTelaDp.toPx()
                val alvoFimY = alvoRect.bottom

                if (alvoCentroY < telaPx / 2f) {
                    // Alvo na metade de cima → cartão embaixo do alvo
                    (alvoFimY + 24.dp.toPx()).toDp()
                } else {
                    // Alvo na metade de baixo → cartão acima do alvo
                    (alvoRect.top - alturaCartaoEstimadaPx - 24.dp.toPx()).toDp()
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