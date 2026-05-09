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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maisdigital.app.core.ui.components.BarraTopo
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.data.catalog.CatalogoApps
import com.maisdigital.app.data.catalog.CatalogoAulasWhatsApp

@Composable
fun ListaAulasScreen(
    appId: String,
    aoVoltar: () -> Unit,
    aoSelecionarAula: (String) -> Unit,
    viewModel: ListaAulasViewModel = viewModel()
) {
    val app = CatalogoApps.buscarPorId(appId)
    val aulas = when (appId) {
        CatalogoApps.ID_WHATSAPP -> CatalogoAulasWhatsApp.aulas
        else -> emptyList()
    }

    val aulasConcluidas by viewModel.aulasConcluidas.collectAsState()
    val avisoVisto by viewModel.avisoSimulacaoVisto.collectAsState()

    if (!avisoVisto && appId == CatalogoApps.ID_WHATSAPP) {
        DialogAvisoSimulacao(
            aoConfirmar = { viewModel.marcarAvisoVisto() }
        )
    }

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