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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.CinzaIconeChat
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.core.ui.theme.FundoChatWhatsApp
import com.maisdigital.app.core.ui.theme.VerdeBolhaMensagem
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppClaro
import com.maisdigital.app.core.ui.theme.VerdeWhatsAppHeader
import com.maisdigital.app.domain.tutorial.alvoTutorial

/**
 * Tela de conversa aberta com a Maria.
 *
 * Alvos disponíveis (alguns só aparecem em momentos específicos):
 *  - "btn_chamada_video"   → ícone de câmera no header
 *  - "campo_mensagem"      → barra de digitação
 *  - "btn_microfone"       → ícone de microfone (modo "não gravando")
 *  - "btn_enviar_audio"    → ícone de stop (modo "gravando")
 *  - "btn_enviar_mensagem" → ícone de avião (quando há texto)
 */
@Composable
fun TelaConversa(
    textoDigitado: String,
    gravandoAudio: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FundoChatWhatsApp)
    ) {
        // Header com avatar + nome + ícone de vídeo
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(VerdeWhatsAppHeader)
                .padding(horizontal = Dimensoes.espacoMedio)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE91E63)),
                contentAlignment = Alignment.Center
            ) {
                Text("M", color = Color.White,
                    style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))
            Text(
                text = "Maria",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            // Ícone de chamada de vídeo
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .alvoTutorial("btn_chamada_video")
            ) {
                Icon(
                    imageVector = Icons.Filled.Videocam,
                    contentDescription = "Chamada de vídeo",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // Bolhas de mensagens fake
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(Dimensoes.espacoMedio),
            verticalArrangement = Arrangement.Top
        ) {
            BolhaRecebida("Oi! Tudo bem?")
            Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))
            BolhaRecebida("Vamos marcar de tomar um café?")

            // Se o usuário "digitou" algo, mostrar como mensagem enviada
            if (textoDigitado.isNotEmpty()) {
                Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))
                BolhaEnviada(textoDigitado)
            }
        }

        // Barra inferior: campo + microfone OU campo + enviar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensoes.espacoPequeno)
        ) {
            // Campo de texto
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(horizontal = Dimensoes.espacoMedio)
                    .alvoTutorial("campo_mensagem")
            ) {
                Text(
                    text = if (textoDigitado.isEmpty()) "Mensagem" else textoDigitado,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (textoDigitado.isEmpty()) CinzaIconeChat
                    else MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))

            // Botão direito: muda conforme contexto
            BotaoAcaoDireita(
                textoDigitado = textoDigitado,
                gravandoAudio = gravandoAudio
            )
        }
    }
}

@Composable
private fun BotaoAcaoDireita(
    textoDigitado: String,
    gravandoAudio: Boolean
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(VerdeWhatsAppClaro)
            .then(
                when {
                    textoDigitado.isNotEmpty() ->
                        Modifier.alvoTutorial("btn_enviar_mensagem")
                    gravandoAudio ->
                        Modifier.alvoTutorial("btn_enviar_audio")
                    else ->
                        Modifier.alvoTutorial("btn_microfone")
                }
            )
    ) {
        val icone = when {
            textoDigitado.isNotEmpty() -> Icons.Filled.Send
            gravandoAudio -> Icons.Filled.Stop
            else -> Icons.Filled.Mic
        }
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun BolhaRecebida(texto: String) {
    Box(
        modifier = Modifier
            .widthIn(max = 280.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(horizontal = Dimensoes.espacoMedio, vertical = Dimensoes.espacoPequeno)
    ) {
        Text(text = texto, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun BolhaEnviada(texto: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(VerdeBolhaMensagem)
                .padding(horizontal = Dimensoes.espacoMedio, vertical = Dimensoes.espacoPequeno)
        ) {
            Text(text = texto, style = MaterialTheme.typography.bodyLarge)
        }
    }
}