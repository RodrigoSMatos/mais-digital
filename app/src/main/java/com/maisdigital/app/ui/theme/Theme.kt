package com.maisdigital.app.core.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.maisdigital.app.core.accessibility.aplicarMultiplicadorFonte
import com.maisdigital.app.domain.model.TamanhoTexto

private val EsquemaCoresClaro = lightColorScheme(
    primary = AzulPrincipal,
    onPrimary = Branco,
    primaryContainer = AzulSuave,
    onPrimaryContainer = AzulPrincipalEscuro,

    secondary = VerdeAcao,
    onSecondary = Branco,
    secondaryContainer = VerdeSuave,
    onSecondaryContainer = VerdeAcao,

    tertiary = AmareloDestaque,
    onTertiary = CinzaTexto,
    tertiaryContainer = AmareloSuave,
    onTertiaryContainer = CinzaTexto,

    error = LaranjaAlerta,
    onError = Branco,

    background = Branco,
    onBackground = CinzaTexto,
    surface = Branco,
    onSurface = CinzaTexto,
    surfaceVariant = CinzaFundo,
    onSurfaceVariant = CinzaTextoSecundario
)

@Composable
fun MaisDigitalTheme(
    darkTheme: Boolean = false,
    tamanhoTexto: TamanhoTexto = TamanhoTexto.PADRAO,
    content: @Composable () -> Unit
) {
    val esquemaCores = EsquemaCoresClaro

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = esquemaCores.primary.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = false
        }
    }

    // Aplica primeiro o theme base, depois sobrescreve a tipografia escalada.
    MaterialTheme(
        colorScheme = esquemaCores,
        typography = TipografiaMaisDigital
    ) {
        MaterialTheme(
            colorScheme = esquemaCores,
            typography = aplicarMultiplicadorFonte(tamanhoTexto),
            content = content
        )
    }
}