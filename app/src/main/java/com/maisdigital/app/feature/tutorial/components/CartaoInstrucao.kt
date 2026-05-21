package com.maisdigital.app.feature.tutorial.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
 * Posicionamento inteligente em TRÊS faixas (topo / meio / base):
 *  - O cartão evita cobrir o ALVO (onde o usuário interage).
 *  - O cartão também evita cobrir o FEEDBACK (onde o resultado aparece),
 *    quando este é diferente do alvo. Ex.: tocar em "inverter câmera" (alvo
 *    na barra inferior) com resultado visível na miniatura (feedback).
 *  - Preferência de posição: TOPO → BASE → MEIO. O meio é o respiro extra
 *    para quando alvo e feedback ocupam tanto a faixa de cima quanto a de
 *    baixo (ou vice-versa).
 */
@Composable
fun CartaoInstrucao(
    instrucao: String,
    progresso: Float,
    indicePasso: Int,
    totalPassos: Int,
    alvoRect: Rect?,
    feedbackRect: Rect? = null,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val density = LocalDensity.current
        val alturaTelaPx = with(density) { maxHeight.toPx() }

        // Altura estimada do cartão (usada para calcular a posição da base
        // e a faixa que ele ocupa). Valor conservador que cobre 3-4 linhas.
        val alturaCartaoPx = with(density) { 250.dp.toPx() }
        val margemPx = with(density) { 32.dp.toPx() }

        // Divide a tela em 3 faixas horizontais. Retorna 0 (topo), 1 (meio)
        // ou 2 (base) para o centro vertical de um retângulo.
        fun faixaDe(rect: Rect?): Int? {
            if (rect == null) return null
            val centroY = rect.center.y
            return when {
                centroY < alturaTelaPx / 3f       -> 0
                centroY < alturaTelaPx * 2f / 3f  -> 1
                else                              -> 2
            }
        }

        val faixasOcupadas = setOfNotNull(faixaDe(alvoRect), faixaDe(feedbackRect))

        // Escolhe a primeira faixa livre na ordem de preferência: topo, base, meio.
        // Se todas estiverem ocupadas (caso raro), cai no topo como padrão seguro.
        val faixaEscolhida = listOf(0, 2, 1).firstOrNull { it !in faixasOcupadas } ?: 0

        val offsetYDp = with(density) {
            when (faixaEscolhida) {
                0 -> margemPx.toDp()                                              // topo
                2 -> (alturaTelaPx - alturaCartaoPx - margemPx).toDp()            // base
                else -> ((alturaTelaPx - alturaCartaoPx) / 2f).toDp()             // meio
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