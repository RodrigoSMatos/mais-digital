package com.maisdigital.app.feature.simuladores.whatsapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.maisdigital.app.data.catalog.CatalogoAulasWhatsApp
import com.maisdigital.app.domain.tutorial.LocalTutorialEngine
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaChamadaVideoAtiva
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaConversa
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaDialogChamadaVideo
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaListaConversas
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaNovoContato

/**
 * Orquestrador do simulador WhatsApp.
 *
 * Decide qual tela mostrar com base no elementoAlvoId do passo atual.
 * Mantém estado interno (texto digitado, áudio gravando, câmera invertida).
 *
 * Filosofia: cada elementoAlvoId implica em uma tela específica.
 * Esse mapeamento está concentrado em [telaParaPasso] para fácil manutenção.
 */
@Composable
fun SimuladorWhatsApp(
    appId: String,
    aulaId: String,
    modifier: Modifier = Modifier
) {
    val engine = LocalTutorialEngine.current
    val state by (engine?.state?.collectAsState() ?: return)

    // Estado interno do simulador
    var textoDigitado by remember { mutableStateOf("") }
    var gravandoAudio by remember { mutableStateOf(false) }
    var cameraInvertida by remember { mutableStateOf(false) }
    var nomeContatoDigitado by remember { mutableStateOf("") }
    var telefoneContatoDigitado by remember { mutableStateOf("") }

    val tutorialState = state ?: return
    val passoAtualId = tutorialState.elementoAlvoId

    // Reagir a passos específicos: simular "ações" do usuário
    LaunchedEffect(passoAtualId) {
        when (passoAtualId) {
            // Aula "Adicionar contato"
            "campo_telefone_contato" -> {
                // Quando chegou no passo do telefone, o nome já foi "preenchido"
                nomeContatoDigitado = "Carlos"
            }
            "btn_salvar_contato" -> {
                telefoneContatoDigitado = "(11) 99999-1234"
            }

            // Aula "Enviar mensagem"
            "btn_enviar_mensagem" -> {
                // Texto foi "digitado" automaticamente
                textoDigitado = "Oi Maria! Tudo bem?"
            }

            // Aula "Enviar áudio"
            "btn_enviar_audio" -> {
                gravandoAudio = true
            }

            // Aula "Inverter câmera"
            "btn_inverter_camera" -> {
                // nada a fazer aqui — só esperando o clique
            }
        }
    }

    // Reagir à conclusão de passos para inverter a câmera
    LaunchedEffect(tutorialState.indicePasso) {
        // Se a aula é "Inverter câmera" e estamos no último passo já avançado
        if (aulaId == CatalogoAulasWhatsApp.ID_AULA_5 &&
            tutorialState.concluida) {
            cameraInvertida = true
        }
    }

    // Decidir tela a mostrar
    val telaAtual = telaParaPasso(passoAtualId)

    when (telaAtual) {
        TelaSimulada.LISTA_CONVERSAS -> TelaListaConversas(modifier.fillMaxSize())
        TelaSimulada.NOVO_CONTATO    -> TelaNovoContato(
            nomeDigitado = nomeContatoDigitado,
            telefoneDigitado = telefoneContatoDigitado,
            modifier = modifier.fillMaxSize()
        )
        TelaSimulada.CONVERSA        -> TelaConversa(
            textoDigitado = textoDigitado,
            gravandoAudio = gravandoAudio,
            modifier = modifier.fillMaxSize()
        )
        TelaSimulada.DIALOG_VIDEO    -> TelaDialogChamadaVideo(modifier.fillMaxSize())
        TelaSimulada.CHAMADA_VIDEO   -> TelaChamadaVideoAtiva(
            cameraInvertida = cameraInvertida,
            modifier = modifier.fillMaxSize()
        )
    }
}

private enum class TelaSimulada {
    LISTA_CONVERSAS,
    NOVO_CONTATO,
    CONVERSA,
    DIALOG_VIDEO,
    CHAMADA_VIDEO
}

/**
 * Mapeamento centralizado: cada elementoAlvoId implica em uma tela.
 * Adicionar novos passos no futuro = adicionar uma linha aqui.
 */
private fun telaParaPasso(passoAtualId: String): TelaSimulada {
    return when (passoAtualId) {
        // Tela: Lista de conversas
        "btn_novo_contato",
        "conversa_maria"           -> TelaSimulada.LISTA_CONVERSAS

        // Tela: Novo contato
        "campo_nome_contato",
        "campo_telefone_contato",
        "btn_salvar_contato"       -> TelaSimulada.NOVO_CONTATO

        // Tela: Conversa aberta
        "btn_chamada_video",
        "campo_mensagem",
        "btn_enviar_mensagem",
        "btn_microfone",
        "btn_enviar_audio"         -> TelaSimulada.CONVERSA

        // Tela: Diálogo de chamada de vídeo
        "btn_confirmar_video"      -> TelaSimulada.DIALOG_VIDEO

        // Tela: Chamada de vídeo ativa
        "btn_inverter_camera"      -> TelaSimulada.CHAMADA_VIDEO

        else                        -> TelaSimulada.LISTA_CONVERSAS
    }
}