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
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaContatos
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaConversa
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaDialogChamadaVideo
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaListaConversas
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaNovoContato

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

    // Auto-preenche valores quando passa para certos passos
    LaunchedEffect(passoAtualId) {
        when (passoAtualId) {
            // Aula 1 — quando chegou no passo do telefone, o nome já foi digitado
            "campo_telefone_contato" -> {
                nomeContatoDigitado = "Carlos"
            }
            "btn_salvar_contato" -> {
                telefoneContatoDigitado = "(11) 99999-1234"
            }

            // Aula 2 — texto auto-aparece
            "btn_enviar_mensagem" -> {
                textoDigitado = "Oi Maria! Tudo bem?"
            }

            // Aula 3 — modo gravando
            "btn_enviar_audio" -> {
                gravandoAudio = true
            }
        }
    }

    // Reagir à conclusão de aulas com efeitos visuais
    LaunchedEffect(tutorialState.indicePasso) {
        if (aulaId == CatalogoAulasWhatsApp.ID_AULA_5 && tutorialState.concluida) {
            cameraInvertida = true
        }
    }

    val telaAtual = telaParaPasso(passoAtualId)

    when (telaAtual) {
        TelaSimulada.LISTA_CONVERSAS -> TelaListaConversas(modifier.fillMaxSize())
        TelaSimulada.CONTATOS        -> TelaContatos(modifier.fillMaxSize())
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
    CONTATOS,
    NOVO_CONTATO,
    CONVERSA,
    DIALOG_VIDEO,
    CHAMADA_VIDEO
}

/**
 * Mapeamento centralizado: cada elementoAlvoId implica em uma tela.
 */
private fun telaParaPasso(passoAtualId: String): TelaSimulada {
    return when (passoAtualId) {
        // Lista de conversas
        "btn_nova_conversa",
        "conversa_maria"           -> TelaSimulada.LISTA_CONVERSAS

        // Tela de Contatos (intermediária da Aula 1)
        "btn_novo_contato"         -> TelaSimulada.CONTATOS

        // Formulário de Novo contato
        "campo_nome_contato",
        "campo_telefone_contato",
        "btn_salvar_contato"       -> TelaSimulada.NOVO_CONTATO

        // Conversa aberta
        "btn_chamada_video",
        "campo_mensagem",
        "btn_enviar_mensagem",
        "btn_microfone",
        "btn_enviar_audio"         -> TelaSimulada.CONVERSA

        "btn_confirmar_video"      -> TelaSimulada.DIALOG_VIDEO

        "btn_inverter_camera"      -> TelaSimulada.CHAMADA_VIDEO

        else                        -> TelaSimulada.LISTA_CONVERSAS
    }
}