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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.MicOff
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
import com.maisdigital.app.domain.tutorial.alvoTutorial

/**
 * Tela de chamada de vídeo "ativa" (simulada).
 *
 * Alvo:
 *  - "btn_inverter_camera" → ícone de inverter câmera no rodapé
 */
@Composable
fun TelaChamadaVideoAtiva(
    cameraInvertida: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {
        // "Vídeo" da Maria preenche tela inteira
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF263238)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE91E63)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "M",
                        color = Color.White,
                        style = MaterialTheme.typography.displayLarge
                    )
                }
                Spacer(modifier = Modifier.size(Dimensoes.espacoMedio))
                Text(
                    text = "Maria",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Text(
                    text = "Chamada de vídeo",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        // Mini-preview da câmera (canto superior direito)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(Dimensoes.espacoMedio)
                .size(width = 90.dp, height = 130.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                .background(if (cameraInvertida) Color(0xFF455A64) else Color(0xFF607D8B)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (cameraInvertida) "Câmera\ntraseira" else "Você",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Barra inferior com ações
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            BotaoCirculo(
                icone = Icons.Filled.MicOff,
                cor = Color.White.copy(alpha = 0.2f),
                tint = Color.White
            )
            BotaoCirculo(
                icone = Icons.Filled.FlipCameraAndroid,
                cor = Color.White.copy(alpha = 0.2f),
                tint = Color.White,
                modifier = Modifier.alvoTutorial("btn_inverter_camera")
            )
            BotaoCirculo(
                icone = Icons.Filled.CallEnd,
                cor = Color(0xFFE53935),
                tint = Color.White
            )
        }
    }
}

@Composable
private fun BotaoCirculo(
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    cor: Color,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(cor)
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(28.dp)
        )
    }
}