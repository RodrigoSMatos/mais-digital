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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.CloseFullscreen
import androidx.compose.material.icons.filled.FlipCameraIos
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.maisdigital.app.core.ui.theme.Dimensoes
import com.maisdigital.app.domain.tutorial.alvoTutorial

/**
 * Tela de Chamada de Vídeo ATIVA — fiel ao WhatsApp real (ver print da tela).
 *
 * Layout:
 *  - Plano de fundo: "vídeo" da outra pessoa (área principal)
 *  - Topo: minimizar | nome + tempo | adicionar pessoa
 *  - Lateral direita: inverter câmera | filtros (visíveis APENAS quando posicionado != EXPANDIDA_PROPRIA)
 *  - Canto inferior direito: miniatura da própria câmera
 *  - Barra inferior (visível quando barrasVisiveis=true): 3 pontinhos | câmera | alto-falante | microfone | encerrar
 *
 * Alvos disponíveis (alguns só fazem sentido em estados específicos):
 *  TOPO:
 *    - "btn_minimizar_chamada"
 *    - "btn_adicionar_pessoa"
 *    - "info_pessoa_chamada"      → área central com nome + tempo (alvo de observação)
 *
 *  LATERAL DIREITA:
 *    - "btn_inverter_camera"
 *    - "btn_filtros"
 *
 *  CENTRO/PRINCIPAL:
 *    - "area_principal_chamada"   → área grande com o vídeo da outra pessoa (observação)
 *
 *  MINIATURA:
 *    - "miniatura_propria_camera" → retrato pequeno (observação ou expandir)
 *
 *  BARRA INFERIOR:
 *    - "btn_barra_inferior"       → área inteira da barra (observação)
 *    - "btn_tres_pontinhos"       → menu de opções
 *    - "btn_camera_chamada"       → liga/desliga câmera
 *    - "btn_alto_falante"         → muda saída de áudio
 *    - "btn_microfone"            → silencia microfone
 *    - "btn_encerrar_chamada"     → botão vermelho
 *
 *  ESTADO EXPANDIDO (própria câmera grande):
 *    - "miniatura_outra_pessoa"   → retrato pequeno da outra pessoa
 *    - "btn_inverter_camera_mini" → inverter câmera dentro da miniatura
 *
 *  MENSAGEM/AVISOS:
 *    - "aviso_microfone_silenciado" → balão "Fulano silenciou o microfone"
 *
 *  MENU 3 PONTINHOS (quando aberto):
 *    - "menu_opcoes_chamada"      → área do menu (observação)
 *    - "opcao_compartilhar_tela"
 *    - "opcao_enviar_mensagem"
 *    - "opcao_levantar_mao"
 *
 *  COMPARTILHAMENTO:
 *    - "dialog_confirmar_compartilhamento"
 *    - "btn_aceitar_compartilhamento"
 *    - "btn_cancelar_compartilhamento"
 *    - "aviso_compartilhamento_ativo"
 *    - "btn_parar_compartilhamento"
 */
@Composable
fun TelaChamadaVideoAtiva(
    cameraDesligada: Boolean,
    microfoneSilenciado: Boolean,
    vivaVozAtivo: Boolean,
    cameraInvertida: Boolean,
    visualizacaoExpandida: Boolean,        // false = mostrando outra pessoa grande / true = mostrando você grande
    menuAberto: Boolean,
    dialogCompartilharAberto: Boolean,
    compartilhandoTela: Boolean,
    tempoChamada: String,
    nomeOutraPessoa: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {
        // ÁREA PRINCIPAL — "vídeo" grande de fundo
        AreaPrincipalChamada(
            visualizacaoExpandida = visualizacaoExpandida,
            nomeOutraPessoa = nomeOutraPessoa,
            modifier = Modifier
                .fillMaxSize()
                .alvoTutorial("area_principal_chamada")
        )

        // Aviso "Fulano silenciou o microfone" — aparece se relevante
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 80.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            BalaoAviso(
                texto = "$nomeOutraPessoa silenciou o microfone.",
                modifier = Modifier.alvoTutorial("aviso_microfone_silenciado")
            )
        }

        // TOPO — Minimizar | Nome+Tempo | Adicionar pessoa
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = Dimensoes.espacoMedio, vertical = Dimensoes.espacoPequeno)
        ) {
            BotaoCirculoEscuro(
                icone = Icons.Filled.CloseFullscreen,
                modifier = Modifier.alvoTutorial("btn_minimizar_chamada")
            )
            Spacer(modifier = Modifier.width(Dimensoes.espacoPequeno))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .alvoTutorial("info_pessoa_chamada")
            ) {
                Text(
                    text = nomeOutraPessoa,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = tempoChamada,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(Dimensoes.espacoPequeno))
            BotaoCirculoEscuro(
                icone = Icons.Filled.PersonAdd,
                modifier = Modifier.alvoTutorial("btn_adicionar_pessoa")
            )
        }

        // LATERAL DIREITA — inverter câmera + filtros
        // Só aparecem quando NÃO está em visualização expandida própria
        if (!visualizacaoExpandida) {
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimensoes.espacoPequeno),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
                    .padding(top = 80.dp, end = Dimensoes.espacoMedio)
            ) {
                BotaoCirculoEscuro(
                    icone = Icons.Filled.FlipCameraIos,
                    modifier = Modifier.alvoTutorial("btn_inverter_camera")
                )
                BotaoCirculoEscuro(
                    icone = Icons.Filled.AutoFixHigh,
                    modifier = Modifier.alvoTutorial("btn_filtros")
                )
            }
        }

        // MINIATURA (canto inferior direito)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = Dimensoes.espacoMedio, bottom = 110.dp)
        ) {
            Miniatura(
                visualizacaoExpandida = visualizacaoExpandida,
                cameraDesligada = cameraDesligada,
                cameraInvertida = cameraInvertida,
                nomeOutraPessoa = nomeOutraPessoa
            )
        }

        // BARRA INFERIOR DE CONTROLES
        BarraControles(
            cameraDesligada = cameraDesligada,
            microfoneSilenciado = microfoneSilenciado,
            vivaVozAtivo = vivaVozAtivo,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // MENU 3 PONTINHOS (overlay)
        if (menuAberto) {
            MenuOpcoes(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 100.dp)
            )
        }

        // DIALOG DE CONFIRMAÇÃO DE COMPARTILHAMENTO
        if (dialogCompartilharAberto) {
            DialogCompartilharTela(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // AVISO DE COMPARTILHAMENTO ATIVO
        if (compartilhandoTela) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(top = 80.dp)
            ) {
                BalaoAviso(
                    texto = "Você está compartilhando sua tela",
                    cor = Color(0xFF075E54),
                    modifier = Modifier.alvoTutorial("aviso_compartilhamento_ativo")
                )
            }
        }
    }
}

// --------------------------------------------------------------------------
// Componentes internos
// --------------------------------------------------------------------------

@Composable
private fun AreaPrincipalChamada(
    visualizacaoExpandida: Boolean,
    nomeOutraPessoa: String,
    modifier: Modifier = Modifier
) {
    // Em "modo normal": grande = outra pessoa
    // Em "modo expandido": grande = você mesmo
    val corFundo = if (visualizacaoExpandida) Color(0xFF37474F) else Color(0xFF455A64)
    val labelGrande = if (visualizacaoExpandida) "Você" else nomeOutraPessoa

    Box(
        modifier = modifier.background(corFundo),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(if (visualizacaoExpandida) Color(0xFF1565C0) else Color(0xFFE91E63)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = labelGrande.first().toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.displayLarge
                )
            }
            Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))
            Text(
                text = labelGrande,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }
    }
}

@Composable
private fun Miniatura(
    visualizacaoExpandida: Boolean,
    cameraDesligada: Boolean,
    cameraInvertida: Boolean,
    nomeOutraPessoa: String
) {
    val largura = 100.dp
    val altura = 150.dp

    // Em "modo expandido" a miniatura mostra a outra pessoa.
    // Em "modo normal" mostra você (ou avatar se câmera desligada).
    val mostraOutra = visualizacaoExpandida
    val rotuloMini = if (mostraOutra) nomeOutraPessoa.first().toString() else "R"
    val corMini = if (mostraOutra) Color(0xFFE91E63) else Color(0xFF455A64)

    Box(
        modifier = Modifier
            .size(width = largura, height = altura)
            .clip(RoundedCornerShape(12.dp))
            .background(corMini)
            .alvoTutorial(
                if (mostraOutra) "miniatura_outra_pessoa" else "miniatura_propria_camera"
            ),
        contentAlignment = Alignment.Center
    ) {
        if (cameraDesligada && !mostraOutra) {
            // Avatar redondo quando câmera desligada
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF607D8B)),
                contentAlignment = Alignment.Center
            ) {
                Text(rotuloMini, color = Color.White, style = MaterialTheme.typography.headlineSmall)
            }
        } else {
            Text(
                text = if (mostraOutra) nomeOutraPessoa else "Você",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Ícone de microfone riscado se silenciado (em cima da miniatura)
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(6.dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.MicOff,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
        }

        // Em modo expandido, mostrar botão de inverter câmera dentro da miniatura
        if (visualizacaoExpandida) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
            ) {
                BotaoCirculoEscuroPequeno(
                    icone = Icons.Filled.FlipCameraIos,
                    modifier = Modifier.alvoTutorial("btn_inverter_camera_mini")
                )
                BotaoCirculoEscuroPequeno(
                    icone = Icons.Filled.AutoFixHigh
                )
            }
        }
    }
}

@Composable
private fun BarraControles(
    cameraDesligada: Boolean,
    microfoneSilenciado: Boolean,
    vivaVozAtivo: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(horizontal = Dimensoes.espacoMedio)
            .alvoTutorial("btn_barra_inferior"),
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        // 3 pontinhos
        BotaoBarra(
            icone = Icons.Filled.MoreHoriz,
            corFundo = Color(0xFF2C2C2C),
            corIcone = Color.White,
            modifier = Modifier.alvoTutorial("btn_tres_pontinhos")
        )

        // Câmera
        BotaoBarra(
            icone = Icons.Outlined.Videocam,
            corFundo = if (cameraDesligada) Color.White else Color(0xFF2C2C2C),
            corIcone = if (cameraDesligada) Color.Black else Color.White,
            modifier = Modifier.alvoTutorial("btn_camera_chamada")
        )

        // Alto-falante
        BotaoBarra(
            icone = Icons.Filled.VolumeUp,
            corFundo = if (vivaVozAtivo) Color.White else Color(0xFF2C2C2C),
            corIcone = if (vivaVozAtivo) Color.Black else Color.White,
            modifier = Modifier.alvoTutorial("btn_alto_falante")
        )

        // Microfone
        BotaoBarra(
            icone = Icons.Filled.MicOff,
            corFundo = if (microfoneSilenciado) Color.White else Color(0xFF2C2C2C),
            corIcone = if (microfoneSilenciado) Color.Black else Color.White,
            modifier = Modifier.alvoTutorial("btn_microfone_chamada")
        )

        // Encerrar (vermelho)
        BotaoBarra(
            icone = Icons.Filled.CallEnd,
            corFundo = Color(0xFFE53935),
            corIcone = Color.White,
            modifier = Modifier.alvoTutorial("btn_encerrar_chamada")
        )

        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Composable
private fun BotaoBarra(
    icone: ImageVector,
    corFundo: Color,
    corIcone: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(corFundo),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = corIcone,
            modifier = Modifier.size(26.dp)
        )
    }
}

@Composable
private fun BotaoCirculoEscuro(
    icone: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun BotaoCirculoEscuroPequeno(
    icone: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun BalaoAviso(
    texto: String,
    cor: Color = Color.Black.copy(alpha = 0.7f),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(cor)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = texto,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun MenuOpcoes(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(horizontal = Dimensoes.espacoMedio)
            .clip(RoundedCornerShape(Dimensoes.raioCantoGrande))
            .background(Color(0xFF2C2C2C))
            .alvoTutorial("menu_opcoes_chamada")
    ) {
        Column(modifier = Modifier.padding(vertical = Dimensoes.espacoPequeno)) {
            ItemMenu(
                icone = Icons.Outlined.Videocam, // placeholder visual
                texto = "Compartilhar tela",
                modifier = Modifier.alvoTutorial("opcao_compartilhar_tela")
            )
            ItemMenu(
                icone = Icons.Filled.PersonAdd,
                texto = "Enviar mensagem",
                modifier = Modifier.alvoTutorial("opcao_enviar_mensagem")
            )
            ItemMenu(
                icone = Icons.Filled.AutoFixHigh,
                texto = "Levantar a mão",
                modifier = Modifier.alvoTutorial("opcao_levantar_mao")
            )
        }
    }
}

@Composable
private fun ItemMenu(
    icone: ImageVector,
    texto: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensoes.espacoMedio,
                vertical = Dimensoes.espacoMedio
            )
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
        Text(
            text = texto,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}

@Composable
private fun DialogCompartilharTela(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensoes.espacoMedio)
            .clip(RoundedCornerShape(Dimensoes.raioCantoGrande))
            .background(Color.White)
            .padding(Dimensoes.espacoMedio)
            .alvoTutorial("dialog_confirmar_compartilhamento")
    ) {
        Column {
            Text(
                text = "Ative a câmera para compartilhar sua tela.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(Dimensoes.espacoMedio))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .alvoTutorial("btn_cancelar_compartilhamento")
                ) {
                    Text(
                        text = "Cancelar",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF075E54)
                    )
                }
                Spacer(modifier = Modifier.width(Dimensoes.espacoMedio))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .alvoTutorial("btn_aceitar_compartilhamento")
                ) {
                    Text(
                        text = "Aceitar",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF075E54)
                    )
                }
            }
        }
    }
}