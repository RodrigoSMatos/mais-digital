package com.maisdigital.app.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes

/**
 * Botão principal grande, pensado para idosos.
 * Altura confortável (64dp), texto generoso, fácil de acertar.
 */
@Composable
fun BotaoGrande(
    texto: String,
    aoClicar: () -> Unit,
    modifier: Modifier = Modifier,
    icone: ImageVector? = null,
    habilitado: Boolean = true
) {
    Button(
        onClick = aoClicar,
        enabled = habilitado,
        shape = RoundedCornerShape(Dimensoes.raioCantoMedio),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensoes.alturaBotaoPrincipal)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icone != null) {
                Icon(
                    imageVector = icone,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(Dimensoes.espacoPequeno))
            }
            Text(
                text = texto,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

/**
 * Variante secundária (contorno) — para ações alternativas.
 */
@Composable
fun BotaoGrandeSecundario(
    texto: String,
    aoClicar: () -> Unit,
    modifier: Modifier = Modifier,
    icone: ImageVector? = null
) {
    OutlinedButton(
        onClick = aoClicar,
        shape = RoundedCornerShape(Dimensoes.raioCantoMedio),
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensoes.alturaBotaoSecundario)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icone != null) {
                Icon(
                    imageVector = icone,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(Dimensoes.espacoPequeno))
            }
            Text(
                text = texto,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}