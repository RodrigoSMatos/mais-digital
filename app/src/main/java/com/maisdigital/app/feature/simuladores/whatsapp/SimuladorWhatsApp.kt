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
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaConfiguracoes
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaContatos
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaConversa
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaGaleriaMidia
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaInfoContato
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaListaConversas
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaNovoContato
import com.maisdigital.app.feature.simuladores.whatsapp.telas.TelaPerfil

/**
 * Estado completo do simulador WhatsApp.
 * Imutável — para mudar algo, faça `copy(...)`.
 */
private data class EstadoSim(
    // Conversa
    val textoDigitado: String = "",
    val gravandoAudio: Boolean = false,
    val audioPausado: Boolean = false,
    val audiosEnviados: Int = 0,
    val menuConversaAberto: Boolean = false,
    val abaGaleria: String = "midia",
    val menuPrincipalAberto: Boolean = false,
    val dialogBloquearAberto: Boolean = false,

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
        TelaSimulada.LISTA_CONVERSAS -> TelaListaConversas(
            menuPrincipalAberto = estado.menuPrincipalAberto,
            modifier = modifier.fillMaxSize()
        )
        TelaSimulada.CONTATOS        -> TelaContatos(modifier.fillMaxSize())
        TelaSimulada.NOVO_CONTATO    -> TelaNovoContato(
            nomeDigitado = estado.nomeContatoDigitado,
            telefoneDigitado = estado.telefoneContatoDigitado,
            modifier = modifier.fillMaxSize()
        )
        TelaSimulada.CONVERSA        -> TelaConversa(
            textoDigitado = estado.textoDigitado,
            gravandoAudio = estado.gravandoAudio,
            audioPausado = estado.audioPausado,
            audiosEnviados = estado.audiosEnviados,
            menuConversaAberto = estado.menuConversaAberto,
            modifier = modifier.fillMaxSize()
        )
        TelaSimulada.INFO_CONTATO    -> TelaInfoContato(
            dialogBloquearAberto = estado.dialogBloquearAberto,
            modifier = modifier.fillMaxSize()
        )
        TelaSimulada.GALERIA_MIDIA   -> TelaGaleriaMidia(
            abaAtiva = estado.abaGaleria,
            modifier = modifier.fillMaxSize()
        )
        TelaSimulada.CONFIGURACOES   -> TelaConfiguracoes(modifier.fillMaxSize())
        TelaSimulada.PERFIL          -> TelaPerfil(modifier.fillMaxSize())
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
            modifier = modifier.fillMaxSize()
        )
    }
}

/**
 * Aplica ação quando um passo é CUMPRIDO (usuário clicou corretamente).
 * Aqui ficam os TOGGLES: ligar/desligar câmera, silenciar, alternar visualização etc.
 */
private fun aplicarAcaoAoConfirmarPasso(atual: EstadoSim, passoId: String): EstadoSim {
    return when (passoId) {
        // Toggles da chamada
        "btn_camera_chamada"       -> atual.copy(cameraDesligada = !atual.cameraDesligada)
        "btn_microfone_chamada"    -> atual.copy(microfoneSilenciado = !atual.microfoneSilenciado)
        "btn_alto_falante"         -> atual.copy(vivaVozAtivo = !atual.vivaVozAtivo)
        "btn_inverter_camera",
        "btn_inverter_camera_mini" -> atual.copy(cameraInvertida = !atual.cameraInvertida)

        // Miniatura: clicar nela alterna visualização (em qualquer aula)
        "miniatura_propria_camera" -> atual.copy(visualizacaoExpandida = true)
        "miniatura_outra_pessoa"   -> atual.copy(visualizacaoExpandida = false)

        // Abrir menu de opções
        "btn_tres_pontinhos"       -> atual.copy(menuAberto = true)

        // ----- Aula 5: tocar nos 3 pontinhos da conversa abre o menu
        "conversa_menu"            -> atual.copy(menuConversaAberto = true)
        // Selecionar "Ver contato" fecha o menu (a tela já vai virar info)
        "menu_ver_contato"         -> atual.copy(menuConversaAberto = false)

        // ----- Aula 6: tocar em cada aba troca o conteúdo da galeria
        "galeria_aba_midia"        -> atual.copy(abaGaleria = "midia")
        "galeria_aba_docs"         -> atual.copy(abaGaleria = "docs")
        "galeria_aba_links"        -> atual.copy(abaGaleria = "links")

        // ----- Aula 7: tocar nos 3 pontinhos da lista abre o menu principal
        "btn_menu_principal"       -> atual.copy(menuPrincipalAberto = true)
        // Tocar em "Configurações" fecha o menu (já vai pra tela de config)
        "menu_principal_configuracoes" -> atual.copy(menuPrincipalAberto = false)

        // ----- Aula 8: tocar em "Bloquear" abre o diálogo de confirmação
        "info_bloquear"            -> atual.copy(dialogBloquearAberto = true)
        // Botões do diálogo fecham ele
        "dialog_bloquear_cancelar",
        "dialog_bloquear_confirmar" -> atual.copy(dialogBloquearAberto = false)

        // Selecionar "Compartilhar tela" → abre diálogo
        "opcao_compartilhar_tela"  -> atual.copy(menuAberto = false, dialogCompartilharAberto = true)

        // ----- Aula 3 (áudio) -----
        // Tocar no microfone → começa a gravar
        "btn_microfone"            -> atual.copy(gravandoAudio = true, audioPausado = false)
        // Enviar áudio → para a gravação, conta +1 balão na conversa
        "btn_enviar_audio"         -> atual.copy(
            gravandoAudio = false,
            audioPausado = false,
            audiosEnviados = atual.audiosEnviados + 1
        )
        // Lixeira → descarta o áudio em gravação (não vira balão)
        "btn_lixeira_audio"        -> atual.copy(gravandoAudio = false, audioPausado = false)
        // Pausar → congela a gravação
        "btn_pausar_audio"         -> atual.copy(audioPausado = true)
        // Retomar → volta a gravar
        "btn_retomar_audio"        -> atual.copy(audioPausado = false)

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
    INFO_CONTATO,
    GALERIA_MIDIA,
    CONFIGURACOES,
    PERFIL,
    CHAMADA_VIDEO
}

/**
 * Mapeia cada elementoAlvoId para a tela que deve aparecer naquele passo.
 */
private fun telaParaPasso(passoAtualId: String): TelaSimulada {
    return when (passoAtualId) {
        "btn_nova_conversa",
        "conversa_pedro",
            // Aula 7: menu de 3 pontinhos da tela principal
        "btn_menu_principal",
        "menu_principal_configuracoes",
            // (alvos de reconhecimento da aula 1 — todos na lista)
        "barra_busca",
        "filtro_todas",
        "filtro_nao_lidas",
        "filtro_favoritos",
        "filtro_grupos",
        "aba_conversas",
        "aba_atualizacoes",
        "aba_ligacoes"             -> TelaSimulada.LISTA_CONVERSAS

        "btn_novo_contato"         -> TelaSimulada.CONTATOS

        "campo_nome_contato",
        "campo_telefone_contato",
        "btn_salvar_contato"       -> TelaSimulada.NOVO_CONTATO

        // Conversa: btn_microfone aqui é o de áudio (Aula 3 antiga)
        "btn_chamada_video",
        "campo_mensagem",
        "btn_enviar_mensagem",
        "btn_microfone",
        "btn_lixeira_audio",
        "btn_pausar_audio",
        "btn_retomar_audio",
        "btn_enviar_audio",
            // Aula 5: formas de abrir info do contato a partir da conversa
        "conversa_foto_contato",
        "conversa_nome_contato",
        "conversa_menu",
        "menu_ver_contato"         -> TelaSimulada.CONVERSA

        // Aula 5: tela de informações do contato
        "info_voltar",
        "info_avatar",
        "info_nome",
        "info_btn_mensagem",
        "info_btn_ligar",
        "info_btn_video",
        "info_btn_buscar",
        "info_midia",
        "info_notificacoes",
        "info_bloquear",
            // Aula 8: diálogo de confirmação de bloquear
        "dialog_bloquear_cancelar",
        "dialog_bloquear_confirmar" -> TelaSimulada.INFO_CONTATO

        // Aula 6: galeria de mídia (abas Mídia/Docs/Links)
        "galeria_voltar",
        "galeria_aba_midia",
        "galeria_aba_docs",
        "galeria_aba_links"        -> TelaSimulada.GALERIA_MIDIA

        // Aula 7: tela de Configurações
        "config_voltar",
        "config_card_perfil"       -> TelaSimulada.CONFIGURACOES

        // Aula 7: tela de Perfil (próprio número)
        "perfil_voltar",
        "perfil_telefone"          -> TelaSimulada.PERFIL

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

        // ----- Aula 3 (áudio) -----
        // Tocar no microfone: a tela precisa mostrar o microfone (NÃO gravando).
        // A gravação só começa ao CONFIRMAR o clique.
        "btn_microfone"          -> atual.copy(gravandoAudio = false, audioPausado = false)
        // Lixeira e pausar: precisam estar gravando e NÃO pausado
        "btn_lixeira_audio",
        "btn_pausar_audio"       -> atual.copy(gravandoAudio = true, audioPausado = false)
        // Retomar: precisa estar gravando e PAUSADO
        "btn_retomar_audio"      -> atual.copy(gravandoAudio = true, audioPausado = true)
        // Enviar áudio: precisa estar gravando e não pausado (estado de envio aplicado ao confirmar)
        "btn_enviar_audio"       -> atual.copy(gravandoAudio = true, audioPausado = false)

        // ----- Aula 5 (abrir info do contato) -----
        // Tocar nos 3 pontinhos: o menu NÃO pode estar aberto ainda
        "conversa_foto_contato",
        "conversa_nome_contato",
        "conversa_menu"          -> atual.copy(menuConversaAberto = false)
        // Tocar em "Ver contato": o menu PRECISA estar aberto
        "menu_ver_contato"       -> atual.copy(menuConversaAberto = true)

        // ----- Aula 6 (mídia, links e docs) -----
        // Ao apontar a aba Mídia, a galeria começa nela
        "galeria_aba_midia"      -> atual.copy(abaGaleria = "midia")

        // ----- Aula 7 (ver próprio número) -----
        // Tocar nos 3 pontinhos da lista: o menu NÃO pode estar aberto ainda
        "btn_menu_principal"     -> atual.copy(menuPrincipalAberto = false)
        // Tocar em "Configurações" no menu: o menu PRECISA estar aberto
        "menu_principal_configuracoes" -> atual.copy(menuPrincipalAberto = true)

        // ----- Aula 8 (bloquear contato) -----
        // Tocar em "Bloquear": o diálogo NÃO pode estar aberto ainda
        "info_bloquear"          -> atual.copy(dialogBloquearAberto = false)
        // Botões do diálogo: o diálogo PRECISA estar aberto
        "dialog_bloquear_cancelar",
        "dialog_bloquear_confirmar" -> atual.copy(dialogBloquearAberto = true)

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