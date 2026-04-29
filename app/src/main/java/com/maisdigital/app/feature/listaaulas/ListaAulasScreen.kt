package com.maisdigital.app.feature.listaaulas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.maisdigital.app.core.ui.components.BarraTopo
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.data.catalog.CatalogoApps
import com.maisdigital.app.data.catalog.CatalogoAulasWhatsApp

/**
 * Lista as aulas disponíveis para um app.
 *
 * No MVP, somente o WhatsApp tem aulas.
 * Por enquanto a lista de aulas concluídas é vazia — Etapa 6 conecta com DataStore.
 */
@Composable
fun ListaAulasScreen(
    appId: String,
    aoVoltar: () -> Unit,
    aoSelecionarAula: (String) -> Unit
) {
    val app = CatalogoApps.buscarPorId(appId)
    val aulas = when (appId) {
        CatalogoApps.ID_WHATSAPP -> CatalogoAulasWhatsApp.aulas
        else -> emptyList()
    }

    // Por enquanto vazio — será preenchido na Etapa 6.
    val aulasConcluidas: Set<String> = emptySet()

    Scaffold(
        topBar = {
            BarraTopo(
                titulo = app?.nome ?: "Aulas",
                aoVoltar = aoVoltar
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimensoes.espacoMedio)
        ) {
            Text(
                text = "Escolha uma aula:",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(
                    top = Dimensoes.espacoGrande,
                    bottom = Dimensoes.espacoMedio
                )
            )

            LazyColumn(
                contentPadding = PaddingValues(vertical = Dimensoes.espacoPequeno),
                verticalArrangement = Arrangement.spacedBy(Dimensoes.espacoMedio)
            ) {
                items(aulas, key = { it.id }) { aula ->
                    CardAula(
                        aula = aula,
                        concluida = aulasConcluidas.contains(aula.id),
                        aoClicar = { aoSelecionarAula(aula.id) }
                    )
                }
            }
        }
    }
}