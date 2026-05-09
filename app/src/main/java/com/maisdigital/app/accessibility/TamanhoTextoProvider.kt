package com.maisdigital.app.core.accessibility

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.maisdigital.app.domain.model.TamanhoTexto

/**
 * Aplica um multiplicador a TODA a tipografia do MaterialTheme.
 * Resultado: textos grandes/médios/pequenos a partir do mesmo theme.
 */
@Composable
fun aplicarMultiplicadorFonte(tamanho: TamanhoTexto): Typography {
    val base = MaterialTheme.typography
    val mult = tamanho.multiplicador

    fun TextStyle.escalar(): TextStyle = copy(
        fontSize = fontSize.escalado(mult),
        lineHeight = lineHeight.escalado(mult)
    )

    return Typography(
        displayLarge = base.displayLarge.escalar(),
        displayMedium = base.displayMedium.escalar(),
        displaySmall = base.displaySmall.escalar(),
        headlineLarge = base.headlineLarge.escalar(),
        headlineMedium = base.headlineMedium.escalar(),
        headlineSmall = base.headlineSmall.escalar(),
        titleLarge = base.titleLarge.escalar(),
        titleMedium = base.titleMedium.escalar(),
        titleSmall = base.titleSmall.escalar(),
        bodyLarge = base.bodyLarge.escalar(),
        bodyMedium = base.bodyMedium.escalar(),
        bodySmall = base.bodySmall.escalar(),
        labelLarge = base.labelLarge.escalar(),
        labelMedium = base.labelMedium.escalar(),
        labelSmall = base.labelSmall.escalar()
    )
}

private fun TextUnit.escalado(mult: Float): TextUnit =
    if (type == TextUnitType.Sp) (value * mult).sp else this