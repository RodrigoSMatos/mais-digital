package com.maisdigital.app.feature.simuladores.whatsapp.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppFAB
import com.maisdigital.app.domain.tutorial.alvoTutorial
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding

/**
 * Tela de Novo Contato — fiel ao WhatsApp real.
 *
 * Estrutura:
 *  - Header branco com voltar + "Novo contato" + ícone de QR
 *  - Campos: Nome, Sobrenome
 *  - Linha com País (BR +55) e Telefone
 *  - Switch "Sincronizar contato com celular"
 *  - Botão Salvar verde GRANDE no rodapé
 *
 * Alvos:
 *  - "campo_nome_contato"     → campo Nome (com borda arredondada)
 *  - "campo_telefone_contato" → campo Telefone
 *  - "btn_salvar_contato"     → botão Salvar verde no rodapé
 */
@Composable
fun TelaNovoContato(
    nomeDigitado: String,
    telefoneDigitado: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header branco
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .windowInsetsPaddingStatusBarsNovoContato()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = Dimensoes.espacoMedio)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
                Text(
                    text = "Novo contato",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

        // Campo Nome
        CampoComBorda(
            icone = Icons.Filled.Person,
            label = "Nome",
            valor = nomeDigitado,
            modifier = Modifier.alvoTutorial("campo_nome_contato")
        )

        Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

        // Campo Sobrenome (sem alvo, só visual)
        CampoComBorda(
            icone = null,
            label = "Sobrenome",
            valor = ""
        )

        Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))

        // Linha com País (BR +55) e Telefone
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensoes.espacoMedio)
        ) {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))

            // Caixa "País BR +55"
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .height(56.dp)
                    .width(110.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp)
            ) {
                Column {
                    Text(
                        text = "País",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "BR +55",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.width(Dimensoes.espacoPequeno))

            // Caixa "Telefone" (alvo)
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .height(56.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp)
                    .alvoTutorial("campo_telefone_contato")
            ) {
                Text(
                    text = if (telefoneDigitado.isEmpty()) "Telefone" else telefoneDigitado,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (telefoneDigitado.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimensoes.espacoGrande))

        // Switch "Sincronizar contato com celular" (só visual)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensoes.espacoMedio)
        ) {
            Icon(
                imageVector = Icons.Filled.Sync,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
            Text(
                text = "Sincronizar contato com celular",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            // Switch desligado (visual)
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(28.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f))
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botão Salvar verde GRANDE no rodapé
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensoes.espacoMedio)
                .height(56.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(VerdeWhatsAppFAB)
                .alvoTutorial("btn_salvar_contato")
        ) {
            Text(
                text = "Salvar",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
    }
}

@Composable
private fun CampoComBorda(
    icone: ImageVector?,
    label: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensoes.espacoMedio)
    ) {
        Box(
            modifier = Modifier.size(28.dp),
            contentAlignment = Alignment.Center
        ) {
            if (icone != null) {
                Icon(
                    imageVector = icone,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = modifier
                .weight(1f)
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = if (valor.isEmpty()) label else valor,
                style = MaterialTheme.typography.titleMedium,
                color = if (valor.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun Modifier.windowInsetsPaddingStatusBarsNovoContato(): Modifier =
    this.windowInsetsPadding(WindowInsets.statusBars)