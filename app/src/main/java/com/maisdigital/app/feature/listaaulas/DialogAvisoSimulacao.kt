package com.maisdigital.app.feature.listaaulas

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maisdigital.app.R

@Composable
fun DialogAvisoSimulacao(
    aoConfirmar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = aoConfirmar,
        icon = {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(
                text = stringResource(R.string.aviso_simulacao_titulo),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            Text(
                text = stringResource(R.string.aviso_simulacao_mensagem),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(onClick = aoConfirmar) {
                Text(
                    text = stringResource(R.string.aviso_simulacao_entendi),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    )
}