package com.maisdigital.app.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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
    // Por enquanto, sempre tema claro. Idosos preferem.
    // Dark theme pode vir em versão futura.
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val esquemaCores = EsquemaCoresClaro

    // Pinta a status bar com a cor primária
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = esquemaCores.primary.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = esquemaCores,
        typography = TipografiaMaisDigital,
        content = content
    )
}