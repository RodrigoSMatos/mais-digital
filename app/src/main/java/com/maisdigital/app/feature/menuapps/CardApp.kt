package com.maisdigital.app.feature.menuapps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maisdigital.app.R
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppClaro
import com.maisdigital.app.data.catalog.CatalogoApps
import com.maisdigital.app.domain.model.AppSimulado

/**
 * Card de cada app na tela de menu.
 * Mostra ícone, nome, descrição e selo "Em breve" se não estiver disponível.
 */
@Composable
fun CardApp(
    app: AppSimulado,
    aoClicar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (icone, corIcone) = iconeEcorParaApp(app.id)

    Card(
        onClick = aoClicar,
        enabled = app.disponivel,
        shape = RoundedCornerShape(Dimensoes.raioCantoGrande),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimensoes.elevacaoCard
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensoes.espacoMedio)
        ) {
            // Ícone redondo
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(Dimensoes.raioCantoMedio)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icone,
                    contentDescription = null,
                    tint = if (app.disponivel) corIcone
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = app.nome,
                        style = MaterialTheme.typography.titleLarge,
                        color = if (app.disponivel) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (!app.disponivel) {
                        Spacer(modifier = Modifier.width(Dimensoes.espacoPequeno))
                        SeloEmBreve()
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = app.descricao,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Selo "Em breve" para apps ainda não disponíveis.
 */
@Composable
private fun SeloEmBreve() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimensoes.raioCantoPequeno))
            .height(28.dp)
    ) {
        Text(
            text = stringResource(R.string.menu_em_breve),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .clip(RoundedCornerShape(Dimensoes.raioCantoPequeno))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

/**
 * Mapeia o id do app para um ícone e uma cor.
 * Em vez de hard-code dentro do CardApp, isolamos aqui para fácil ajuste.
 */

private fun iconeEcorParaApp(appId: String): Pair<ImageVector, androidx.compose.ui.graphics.Color> {
    return when (appId) {
        CatalogoApps.ID_WHATSAPP -> Icons.Filled.Chat to VerdeWhatsAppClaro
        CatalogoApps.ID_GMAIL    -> Icons.Filled.Email to androidx.compose.ui.graphics.Color(0xFFD93025)
        CatalogoApps.ID_MAPS     -> Icons.Filled.Map to androidx.compose.ui.graphics.Color(0xFF1A73E8)
        else                     -> Icons.Filled.Chat to androidx.compose.ui.graphics.Color(0xFF1565C0)
    }
}
