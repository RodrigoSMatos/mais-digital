package com.maisdigital.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.maisdigital.app.core.ui.theme.MaisDigitalTheme
import com.maisdigital.app.domain.model.TamanhoTexto
import com.maisdigital.app.navigation.NavegacaoMaisDigital

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val configRepository = (application as MaisDigitalApp).configRepository

        setContent {
            val tamanhoTexto by configRepository.tamanhoTexto
                .collectAsState(initial = TamanhoTexto.PADRAO)

            MaisDigitalTheme(tamanhoTexto = tamanhoTexto) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavegacaoMaisDigital()
                }
            }
        }
    }
}