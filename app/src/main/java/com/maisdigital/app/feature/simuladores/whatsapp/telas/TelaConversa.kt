package com.maisdigital.app.feature.simuladores.whatsapp.telas

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
 * Tela de conversa aberta com o Pedro.
 *
 * Alvos disponíveis (alguns só aparecem em momentos específicos):
 *  - "btn_chamada_video"   → ícone de câmera no header
 *  - "campo_mensagem"      → barra de digitação
 *  - "btn_microfone"       → microfone (modo "não gravando")
 *  - "btn_enviar_mensagem" → avião (quando há texto digitado)
 *  Durante a gravação (barra de gravação):
 *  - "btn_lixeira_audio"   → apagar o áudio antes de enviar
 *  - "btn_pausar_audio"    → pausar a gravação (vira microfone quando pausado)
 *  - "btn_retomar_audio"   → retomar a gravação pausada
 *  - "btn_enviar_audio"    → avião verde, envia o áudio
 */
@Composable
fun TelaConversa(
    textoDigitado: String,
    gravandoAudio: Boolean,
    audioPausado: Boolean = false,
    audiosEnviados: Int = 0,
    menuConversaAberto: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FundoChatWhatsApp)
    ) {
        // Header com avatar + nome + ícone de vídeo
        // Column externa aplica statusBars padding pra empurrar o conteúdo
        // pra baixo da barra de status, sem deixar barra branca em cima.

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(VerdeWhatsAppHeader)
                .windowInsetsPadding(androidx.compose.foundation.layout.WindowInsets.statusBars)
        ) {
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = Dimensoes.espacoMedio)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00897B))
                            .alvoTutorial("conversa_foto_contato"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("P", color = Color.White,
                            style = MaterialTheme.typography.titleMedium)
                    }
                    Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))
                    Text(
                        text = "Pedro Borba",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier
                            .weight(1f)
                            .alvoTutorial("conversa_nome_contato")
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
                    // Ícone de 3 pontinhos (menu da conversa)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(48.dp)
                            .alvoTutorial("conversa_menu")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Mais opções",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Menu suspenso do botão 3 pontinhos (abre na aula de info do contato)
                if (menuConversaAberto) {
                    MenuConversa(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 4.dp, end = 4.dp)
                    )
                }
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
            BolhaRecebida("Bom te ver!")

            // Se o usuário "digitou" algo, mostrar como mensagem enviada
            if (textoDigitado.isNotEmpty()) {
                Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))
                BolhaEnviada(textoDigitado)
            }

            // Cada áudio enviado vira um balão de registro na conversa.
            repeat(audiosEnviados) {
                Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))
                BolhaAudioEnviada(duracao = "0:05")
            }
        }

        // Barra inferior. Três modos:
        //  - Não gravando: campo de texto + botão microfone/enviar
        //  - Gravando: lixeira | (tempo + waveform animado) | pausa | avião
        //  - Pausado: lixeira | (tempo + waveform congelado) | microfone | avião
        if (gravandoAudio) {
            BarraGravacao(
                audioPausado = audioPausado,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensoes.espacoPequeno)
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensoes.espacoPequeno)
            ) {
                // Campo de texto normal
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

                // Botão microfone (ou enviar mensagem, quando há texto)
                BotaoAcaoDireita(textoDigitado = textoDigitado)
            }
        }
    }
}


/**
 * Waveform animado de gravação — barras verticais que sobem e descem
 * continuamente, simulando captação de áudio ao vivo. Cada barra tem uma
 * fase própria pra dar a sensação de movimento orgânico.
 */
@Composable
private fun WaveformGravacao(modifier: Modifier = Modifier) {
    // Alturas relativas base de cada barra (0.0 a 1.0). O padrão se repete
    // pra preencher a largura disponível.
    val alturasBase = listOf(
        0.3f, 0.6f, 0.9f, 0.5f, 0.7f, 0.4f, 1.0f, 0.5f,
        0.8f, 0.3f, 0.6f, 0.9f, 0.4f, 0.7f, 0.5f, 0.8f,
        0.3f, 0.6f, 0.4f, 0.9f, 0.5f, 0.7f, 0.3f, 0.6f
    )

    val transicao = rememberInfiniteTransition(label = "waveform")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier.height(28.dp)
    ) {
        alturasBase.forEachIndexed { indice, alturaBase ->
            // Cada barra anima com uma defasagem, criando movimento de "onda".
            val fator by transicao.animateFloat(
                initialValue = 0.4f,
                targetValue = 1.0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 500 + (indice % 5) * 90),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "barra_$indice"
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(alturaBase * fator)
                    .clip(RoundedCornerShape(2.dp))
                    .background(VerdeWhatsAppClaro)
            )
        }
    }
}


/**
 * Barra exibida durante a gravação de áudio. Layout fiel ao WhatsApp:
 *   lixeira | (tempo + waveform) | pausar-ou-microfone | enviar (avião)
 *
 * - Gravando (não pausado): waveform ANIMA, botão central é PAUSA.
 * - Pausado: waveform CONGELA, botão central é MICROFONE (retomar).
 */
@Composable
private fun BarraGravacao(
    audioPausado: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // Lixeira (apagar áudio antes de enviar)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .alvoTutorial("btn_lixeira_audio")
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Apagar áudio",
                tint = CinzaIconeChat,
                modifier = Modifier.size(28.dp)
            )
        }

        // Faixa central: tempo + waveform (animado ou congelado)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(horizontal = Dimensoes.espacoMedio)
        ) {
            Text(
                text = "0:03",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))
            if (audioPausado) {
                // Congelado: usa o waveform estático
                WaveformEstatico(modifier = Modifier.weight(1f, fill = false))
            } else {
                WaveformGravacao(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))

        // Botão central de controle: pausa (gravando) ou microfone (pausado)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .then(
                    if (audioPausado) Modifier.alvoTutorial("btn_retomar_audio")
                    else Modifier.alvoTutorial("btn_pausar_audio")
                )
        ) {
            Icon(
                imageVector = if (audioPausado) Icons.Filled.Mic else Icons.Filled.Pause,
                contentDescription = if (audioPausado) "Continuar gravando" else "Pausar",
                tint = Color(0xFFE53935),  // vermelho
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))

        // Enviar (avião verde)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(VerdeWhatsAppClaro)
                .alvoTutorial("btn_enviar_audio")
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Enviar áudio",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
private fun BotaoAcaoDireita(
    textoDigitado: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(VerdeWhatsAppClaro)
            .then(
                if (textoDigitado.isNotEmpty())
                    Modifier.alvoTutorial("btn_enviar_mensagem")
                else
                    Modifier.alvoTutorial("btn_microfone")
            )
    ) {
        Icon(
            imageVector = if (textoDigitado.isNotEmpty()) Icons.Filled.Send else Icons.Filled.Mic,
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

/**
 * Balão de áudio enviado (registro na conversa após o envio).
 * Mostra avatar + play + waveform estático + duração, fiel ao WhatsApp real.
 */
@Composable
private fun BolhaAudioEnviada(duracao: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(VerdeBolhaMensagem)
                .padding(horizontal = Dimensoes.espacoMedio, vertical = Dimensoes.espacoPequeno)
        ) {
            // Botão play
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Reproduzir áudio",
                tint = CinzaIconeChat,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))
            // Waveform estático (palitinhos parados)
            WaveformEstatico(modifier = Modifier.weight(1f, fill = false))
            Spacer(modifier = Modifier.size(Dimensoes.espacoPequeno))
            // Duração
            Text(
                text = duracao,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * Menu suspenso que abre ao tocar nos 3 pontinhos do header da conversa.
 * No tutorial, mostra a opção "Ver contato" (alvo: menu_ver_contato), que
 * é uma das formas de abrir as informações do contato.
 */
@Composable
private fun MenuConversa(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .widthIn(min = 200.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(vertical = Dimensoes.espacoPequeno)
    ) {
        ItemMenuConversa(
            texto = "Ver contato",
            modifier = Modifier.alvoTutorial("menu_ver_contato")
        )
        ItemMenuConversa(texto = "Mídia, links e docs")
        ItemMenuConversa(texto = "Buscar")
        ItemMenuConversa(texto = "Silenciar notificações")
    }
}

@Composable
private fun ItemMenuConversa(
    texto: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensoes.espacoMedio, vertical = Dimensoes.espacoMedio)
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Waveform estático (sem animação) — usado no balão de áudio já enviado.
 * Barras de alturas fixas, representando o áudio gravado.
 */
@Composable
private fun WaveformEstatico(modifier: Modifier = Modifier) {
    val alturas = listOf(
        0.4f, 0.7f, 1.0f, 0.5f, 0.8f, 0.3f, 0.6f, 0.9f,
        0.4f, 0.7f, 0.5f, 1.0f, 0.6f, 0.3f, 0.8f, 0.5f
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier.height(24.dp)
    ) {
        alturas.forEach { altura ->
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight(altura)
                    .clip(RoundedCornerShape(2.dp))
                    .background(CinzaIconeChat)
            )
        }
    }
}