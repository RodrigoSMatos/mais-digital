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
    var nomeContatoDigitado by remember { mutableStateOf("") }
    var telefoneContatoDigitado by remember { mutableStateOf("") }

    // Estado da chamada de vídeo
    var cameraDesligada by remember { mutableStateOf(false) }
    var microfoneSilenciado by remember { mutableStateOf(false) }
    var vivaVozAtivo by remember { mutableStateOf(false) }
    var cameraInvertida by remember { mutableStateOf(false) }
    var visualizacaoExpandida by remember { mutableStateOf(false) }
    var menuAberto by remember { mutableStateOf(false) }
    var dialogCompartilharAberto by remember { mutableStateOf(false) }
    var compartilhandoTela by remember { mutableStateOf(false) }

    val tutorialState = state ?: return
    val passoAtualId = tutorialState.elementoAlvoId

    LaunchedEffect(passoAtualId) {
        when (passoAtualId) {
            // ----- Aula 1 (adicionar contato)
            "campo_telefone_contato" -> nomeContatoDigitado = "Carlos"
            "btn_salvar_contato"     -> telefoneContatoDigitado = "(11) 99999-1234"

            // ----- Aula 2 (enviar mensagem)
            "btn_enviar_mensagem" -> textoDigitado = "Oi Maria! Tudo bem?"

            // ----- Aula 3 (enviar áudio)
            "btn_enviar_audio" -> gravandoAudio = true
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
        TelaSimulada.CHAMADA_VIDEO   -> TelaChamadaVideoAtiva(
            cameraDesligada = cameraDesligada,
            microfoneSilenciado = microfoneSilenciado,
            vivaVozAtivo = vivaVozAtivo,
            cameraInvertida = cameraInvertida,
            visualizacaoExpandida = visualizacaoExpandida,
            menuAberto = menuAberto,
            dialogCompartilharAberto = dialogCompartilharAberto,
            compartilhandoTela = compartilhandoTela,
            tempoChamada = "3:36",
            nomeOutraPessoa = "Maria",
            modifier = modifier.fillMaxSize()
        )
    }
}

private enum class TelaSimulada {
    LISTA_CONVERSAS,
    CONTATOS,
    NOVO_CONTATO,
    CONVERSA,
    CHAMADA_VIDEO
}

private fun telaParaPasso(passoAtualId: String): TelaSimulada {
    return when (passoAtualId) {
        // Lista de conversas
        "btn_nova_conversa",
        "conversa_maria"           -> TelaSimulada.LISTA_CONVERSAS

        // Tela de Contatos
        "btn_novo_contato"         -> TelaSimulada.CONTATOS

        // Novo contato
        "campo_nome_contato",
        "campo_telefone_contato",
        "btn_salvar_contato"       -> TelaSimulada.NOVO_CONTATO

        // Conversa aberta (Aulas 2-3 antigas e iniciar chamada)
        "btn_chamada_video",
        "campo_mensagem",
        "btn_enviar_mensagem",
        "btn_microfone",
        "btn_enviar_audio"         -> TelaSimulada.CONVERSA

        // Tudo da chamada de vídeo (todas as 6 novas aulas)
        "btn_minimizar_chamada",
        "info_pessoa_chamada",
        "btn_adicionar_pessoa",
        "btn_inverter_camera",
        "btn_filtros",
        "area_principal_chamada",
        "miniatura_propria_camera",
        "miniatura_outra_pessoa",
        "btn_inverter_camera_mini",
        "btn_barra_inferior",
        "btn_tres_pontinhos",
        "btn_camera_chamada",
        "btn_alto_falante",
        "btn_encerrar_chamada",
        "aviso_microfone_silenciado",
        "menu_opcoes_chamada",
        "opcao_compartilhar_tela",
        "opcao_enviar_mensagem",
        "opcao_levantar_mao",
        "dialog_confirmar_compartilhamento",
        "btn_aceitar_compartilhamento",
        "btn_cancelar_compartilhamento",
        "aviso_compartilhamento_ativo",
        "btn_parar_compartilhamento"   -> TelaSimulada.CHAMADA_VIDEO

        else                       -> TelaSimulada.LISTA_CONVERSAS
    }
}