package com.maisdigital.app.feature.menuapps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maisdigital.app.R
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.data.catalog.CatalogoApps

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuAppsScreen(
    aoSelecionarApp: (String) -> Unit,
    aoAbrirConfiguracoes: () -> Unit = {}
) {
    val apps = CatalogoApps.apps

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = aoAbrirConfiguracoes) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Configurações",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
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