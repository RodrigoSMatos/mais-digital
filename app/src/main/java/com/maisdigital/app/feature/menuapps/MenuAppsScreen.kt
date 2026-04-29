package com.maisdigital.app.feature.menuapps

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
import androidx.compose.ui.res.stringResource
import com.maisdigital.app.R
import com.maisdigital.app.core.ui.components.BarraTopo
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.data.catalog.CatalogoApps

/**
 * Menu inicial. Lista todos os apps que o usuário pode aprender.
 * Apps com disponivel=false aparecem com selo "Em breve" e ficam não-clicáveis.
 */
@Composable
fun MenuAppsScreen(
    aoSelecionarApp: (String) -> Unit
) {
    val apps = CatalogoApps.apps

    Scaffold(
        topBar = {
            BarraTopo(titulo = stringResource(R.string.app_name))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimensoes.espacoMedio)
        ) {
            Text(
                text = stringResource(R.string.menu_titulo),
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
                items(apps, key = { it.id }) { app ->
                    CardApp(
                        app = app,
                        aoClicar = { aoSelecionarApp(app.id) }
                    )
                }
            }
        }
    }
}