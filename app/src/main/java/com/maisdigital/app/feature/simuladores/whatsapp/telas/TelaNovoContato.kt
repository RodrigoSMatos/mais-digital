package com.maisdigital.app.feature.simuladores.whatsapp.telas

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppClaro
import com.maisdigital.app.domain.tutorial.alvoTutorial
import com.maisdigital.app.feature.simuladores.whatsapp.components.HeaderWhatsApp

/**
 * Tela de novo contato — formulário simulado.
 *
 * Alvos:
 *  - "campo_nome_contato"     → primeiro campo
 *  - "campo_telefone_contato" → segundo campo
 *  - "btn_salvar_contato"     → botão verde no rodapé
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
        HeaderWhatsApp(titulo = "Novo contato")

        Spacer(modifier = Modifier.size(Dimensoes.espacoGrande))

        // Campo Nome
        CampoFormulario(
            icone = Icons.Filled.Person,
            label = "Nome",
            valor = nomeDigitado,
            placeholder = "Digite o nome",
            modifier = Modifier.alvoTutorial("campo_nome_contato")
        )

        Spacer(modifier = Modifier.size(Dimensoes.espacoMedio))

        // Campo Telefone
        CampoFormulario(
            icone = Icons.Filled.Phone,
            label = "Telefone",
            valor = telefoneDigitado,
            placeholder = "Digite o telefone",
            modifier = Modifier.alvoTutorial("campo_telefone_contato")
        )

        Spacer(modifier = Modifier.weight(1f))

        // Botão Salvar
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensoes.espacoMedio)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(Dimensoes.raioCantoMedio))
                    .background(VerdeWhatsAppClaro)
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
}

@Composable
private fun CampoFormulario(
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    valor: String,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensoes.espacoMedio, vertical = Dimensoes.espacoPequeno)
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.size(Dimensoes.espacoMedio))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = if (valor.isEmpty()) placeholder else valor,
                style = MaterialTheme.typography.titleLarge,
                color = if (valor.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant
                else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.size(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))
            )
        }
    }
}