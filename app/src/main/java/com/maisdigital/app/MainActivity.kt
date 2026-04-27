package com.maisdigital.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.maisdigital.app.core.ui.theme.MaisDigitalTheme
import com.maisdigital.app.navigation.NavegacaoMaisDigital

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaisDigitalTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavegacaoMaisDigital()
                }
            }
        }
    }
}