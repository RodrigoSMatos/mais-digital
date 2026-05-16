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
import com.maisdigital.app.domain.tutorial.LocalTutorialEngine
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaChamadaVideoAtiva
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaContatos
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaConversa
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaListaConversas
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaNovoContato

/**
 * Aulas em que a miniatura própria deve ser INTERATIVA (clicável, registrada
 * como alvo do tutorial, capaz de alternar a visualização).
 *
 * Em todas as outras aulas, a miniatura é puramente decorativa — faz parte
 * do "cenário" da chamada, mas não dispara nenhuma ação nem aparece como
 * alvo do tutorial. Isso evita que aulas de reconhecimento (como a Aula 4)
 * acabem caindo num estado expandido onde o vídeo do Pedro vira um simples
 * retângulo com nome em texto.
 *
 * Para adicionar futuras aulas que precisem da miniatura interativa, basta
 * incluir o id da aula neste conjunto.
 */
private val AULAS_COM_MINIATURA_INTERATIVA = setOf("wpp_aula_7")

/**
 * Estado completo do simulador WhatsApp.
 * Imutável — para mudar algo, faça `copy(...)`.
 */
private data class EstadoSim(
    // Conversa
    val textoDigitado: String = "",
    val gravandoAudio: Boolean = false,

    // Novo contato
    val nomeContatoDigitado: String = "",
    val telefoneContatoDigitado: String = "",

    // Chamada de vídeo
    val cameraDesligada: Boolean = false,
    val microfoneSilenciado: Boolean = false,
    val vivaVozAtivo: Boolean = false,
    val cameraInvertida: Boolean = false,
    val visualizacaoExpandida: Boolean = false,
    val menuAberto: Boolean = false,
    val dialogCompartilharAberto: Boolean = false,
    val compartilhandoTela: Boolean = false
)

@Composable
fun SimuladorWhatsApp(
    appId: String,
    aulaId: String,
    modifier: Modifier = Modifier
) {
    val engine = LocalTutorialEngine.current
    val state by (engine?.state?.collectAsState() ?: return)

    // Reseta estado ao trocar de aula
    var estado by remember(aulaId) { mutableStateOf(EstadoSim()) }

    val tutorialState = state ?: return
    val passoAtualId = tutorialState.elementoAlvoId

    // Rastreia o ÍNDICE do último passo confirmado.
    // Usar índice (não id) garante que cada confirmação seja única, mesmo
    // quando dois passos consecutivos têm o mesmo elementoAlvoId.
    // -1 = nada confirmado ainda nesta aula.
    var indiceUltimoConfirmado by remember(aulaId) { mutableStateOf(-1) }

    // Aplica ajuste de estado SEMPRE que entra num passo novo
    LaunchedEffect(passoAtualId) {
        estado = aplicarAcaoAoEntrarNoPasso(estado, passoAtualId)
    }

    // Aplica ação de TOGGLE quando o passo anterior foi CUMPRIDO
    LaunchedEffect(tutorialState.indicePasso) {
        val aula = tutorialState.aula
        val indiceAnterior = tutorialState.indicePasso - 1
        if (indiceAnterior >= 0 &&
            indiceAnterior < aula.passos.size &&
            indiceAnterior != indiceUltimoConfirmado
        ) {
            indiceUltimoConfirmado = indiceAnterior
            val passoAnteriorId = aula.passos[indiceAnterior].elementoAlvoId
            estado = aplicarAcaoAoConfirmarPasso(estado, passoAnteriorId)
        }
    }

    val telaAtual = telaParaPasso(passoAtualId)

    when (telaAtual) {
        TelaSimulada.LISTA_CONVERSAS -> TelaListaConversas(modifier.fillMaxSize())
        TelaSimulada.CONTATOS        -> TelaContatos(modifier.fillMaxSize())
        TelaSimulada.NOVO_CONTATO    -> TelaNovoContato(
            nomeDigitado = estado.nomeContatoDigitado,
            telefoneDigitado = estado.telefoneContatoDigitado,
            modifier = modifier.fillMaxSize()
        )
        TelaSimulada.CONVERSA        -> TelaConversa(
            textoDigitado = estado.textoDigitado,
            gravandoAudio = estado.gravandoAudio,
            modifier = modifier.fillMaxSize()
        )
        TelaSimulada.CHAMADA_VIDEO   -> TelaChamadaVideoAtiva(
            cameraDesligada = estado.cameraDesligada,
            microfoneSilenciado = estado.microfoneSilenciado,
            vivaVozAtivo = estado.vivaVozAtivo,
            cameraInvertida = estado.cameraInvertida,
            visualizacaoExpandida = estado.visualizacaoExpandida,
            menuAberto = estado.menuAberto,
            dialogCompartilharAberto = estado.dialogCompartilharAberto,
            compartilhandoTela = estado.compartilhandoTela,
            tempoChamada = "3:36",
            nomeOutraPessoa = "Pedro Borba",
            miniaturaInterativa = aulaId in AULAS_COM_MINIATURA_INTERATIVA,
            modifier = modifier.fillMaxSize()
        )
    }
}

/**
 * Aplica ação quando um passo é CUMPRIDO (usuário clicou corretamente).
 * Aqui ficam os TOGGLES: ligar/desligar câmera, silenciar, etc.
 */
private fun aplicarAcaoAoConfirmarPasso(atual: EstadoSim, passoId: String): EstadoSim {
    return when (passoId) {
        // Toggles da chamada
        "btn_camera_chamada"       -> atual.copy(cameraDesligada = !atual.cameraDesligada)
        "btn_microfone_chamada"    -> atual.copy(microfoneSilenciado = !atual.microfoneSilenciado)
        "btn_alto_falante"         -> atual.copy(vivaVozAtivo = !atual.vivaVozAtivo)
        "btn_inverter_camera",
        "btn_inverter_camera_mini" -> atual.copy(cameraInvertida = !atual.cameraInvertida)

        // Miniatura: clicar nela alterna visualização
        "miniatura_propria_camera" -> atual.copy(visualizacaoExpandida = true)
        "miniatura_outra_pessoa"   -> atual.copy(visualizacaoExpandida = false)

        // Abrir menu de opções
        "btn_tres_pontinhos"       -> atual.copy(menuAberto = true)

        // Selecionar "Compartilhar tela" → abre diálogo
        "opcao_compartilhar_tela"  -> atual.copy(menuAberto = false, dialogCompartilharAberto = true)

        // Aceitar → começa compartilhamento
        "btn_aceitar_compartilhamento" -> atual.copy(
            dialogCompartilharAberto = false,
            compartilhandoTela = true
        )
        "btn_cancelar_compartilhamento" -> atual.copy(dialogCompartilharAberto = false)

        else -> atual
    }
}

private enum class TelaSimulada {
    LISTA_CONVERSAS,
    CONTATOS,
    NOVO_CONTATO,
    CONVERSA,
    CHAMADA_VIDEO
}

/**
 * Mapeia cada elementoAlvoId para a tela que deve aparecer naquele passo.
 */
private fun telaParaPasso(passoAtualId: String): TelaSimulada {
    return when (passoAtualId) {
        "btn_nova_conversa",
        "conversa_pedro"           -> TelaSimulada.LISTA_CONVERSAS

        "btn_novo_contato"         -> TelaSimulada.CONTATOS

        "campo_nome_contato",
        "campo_telefone_contato",
        "btn_salvar_contato"       -> TelaSimulada.NOVO_CONTATO

        // Conversa: btn_microfone aqui é o de áudio (Aula 3 antiga)
        "btn_chamada_video",
        "campo_mensagem",
        "btn_enviar_mensagem",
        "btn_microfone",
        "btn_enviar_audio"         -> TelaSimulada.CONVERSA

        // Chamada: btn_microfone_chamada é o de silenciar
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
        "btn_microfone_chamada",
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
        "btn_parar_compartilhamento" -> TelaSimulada.CHAMADA_VIDEO

        else                       -> TelaSimulada.LISTA_CONVERSAS
    }
}


/**
 * Ao ENTRAR num passo, ajusta o estado para que a tela esteja no contexto certo
 * PARA O PASSO ATUAL (não para o anterior).
 *
 * Regra de ouro: cada passo entra no estado em que ele precisa estar para
 * o usuário poder executá-lo. Ex: se o passo é "toque na opção compartilhar
 * tela", o menu precisa já estar aberto. Se o passo é "toque nos três pontinhos",
 * o menu NÃO pode estar aberto.
 */
private fun aplicarAcaoAoEntrarNoPasso(atual: EstadoSim, passoId: String): EstadoSim {
    return when (passoId) {
        // ----- Aula 1 (adicionar contato): preenchimento automático
        "campo_telefone_contato" -> atual.copy(nomeContatoDigitado = "Carlos")
        "btn_salvar_contato"     -> atual.copy(telefoneContatoDigitado = "(11) 99999-1234")

        // ----- Aula 2 (mensagem)
        "btn_enviar_mensagem"    -> atual.copy(textoDigitado = "Oi Pedro! Tudo bem?")

        // ----- Aula 3 (áudio)
        "btn_enviar_audio"       -> atual.copy(gravandoAudio = true)

        // ----- Aulas de chamada -----
        // Volta ao estado padrão da chamada quando o passo é apenas iniciar
        "btn_chamada_video"      -> atual.copy(
            menuAberto = false,
            dialogCompartilharAberto = false,
            compartilhandoTela = false
        )

        // Passos onde o menu PRECISA estar aberto
        "opcao_compartilhar_tela",
        "opcao_enviar_mensagem",
        "opcao_levantar_mao",
        "menu_opcoes_chamada"    -> atual.copy(menuAberto = true)

        // Passos onde o menu NÃO pode estar aberto (chegamos antes de abrir)
        "btn_tres_pontinhos"     -> atual.copy(menuAberto = false)

        // Passos onde a visualização PRECISA estar expandida (você grande)
        "miniatura_outra_pessoa" -> atual.copy(visualizacaoExpandida = true)

        // Passos onde a visualização PRECISA estar normal (Pedro grande)
        "miniatura_propria_camera" -> atual.copy(visualizacaoExpandida = false)

        // Passos do compartilhamento de tela: estados intermediários
        "btn_aceitar_compartilhamento",
        "btn_cancelar_compartilhamento" -> atual.copy(
            menuAberto = false,
            dialogCompartilharAberto = true
        )
        "aviso_compartilhamento_ativo" -> atual.copy(
            menuAberto = false,
            dialogCompartilharAberto = false,
            compartilhandoTela = true
        )

        // Demais passos: estado não muda
        else -> atual
    }
}