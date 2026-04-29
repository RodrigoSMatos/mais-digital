package com.maisdigital.app.data.catalog

import com.maisdigital.app.domain.model.Aula
import com.maisdigital.app.domain.model.Passo

/**
 * As 5 aulas do módulo WhatsApp.
 *
 * Cada elementoAlvoId deve corresponder exatamente ao id usado
 * em Modifier.alvoTutorial(id = "...") nas telas do simulador.
 */
object CatalogoAulasWhatsApp {

    const val ID_AULA_1 = "wpp_aula_1"
    const val ID_AULA_2 = "wpp_aula_2"
    const val ID_AULA_3 = "wpp_aula_3"
    const val ID_AULA_4 = "wpp_aula_4"
    const val ID_AULA_5 = "wpp_aula_5"

    val aulas: List<Aula> = listOf(

        // ------------------------------------------------------------------
        // Aula 1 — Adicionar contato
        // ------------------------------------------------------------------
        Aula(
            id = ID_AULA_1,
            appId = CatalogoApps.ID_WHATSAPP,
            ordem = 1,
            titulo = "Adicionar um contato",
            descricao = "Nesta aula você vai aprender a adicionar uma pessoa nova " +
                    "na sua lista de contatos do WhatsApp.",
            passos = listOf(
                Passo(
                    id = "wpp1_p1",
                    instrucao = "Toque no ícone de novo contato (o lápis) no canto da tela.",
                    elementoAlvoId = "btn_novo_contato"
                ),
                Passo(
                    id = "wpp1_p2",
                    instrucao = "Toque no campo 'Nome' para digitar o nome da pessoa.",
                    elementoAlvoId = "campo_nome_contato"
                ),
                Passo(
                    id = "wpp1_p3",
                    instrucao = "Agora toque no campo 'Telefone' para digitar o número.",
                    elementoAlvoId = "campo_telefone_contato"
                ),
                Passo(
                    id = "wpp1_p4",
                    instrucao = "Toque em 'Salvar' para guardar o novo contato.",
                    elementoAlvoId = "btn_salvar_contato"
                )
            )
        ),

        // ------------------------------------------------------------------
        // Aula 2 — Enviar mensagem
        // ------------------------------------------------------------------
        Aula(
            id = ID_AULA_2,
            appId = CatalogoApps.ID_WHATSAPP,
            ordem = 2,
            titulo = "Enviar uma mensagem",
            descricao = "Nesta aula você vai aprender a abrir uma conversa " +
                    "e enviar uma mensagem de texto.",
            passos = listOf(
                Passo(
                    id = "wpp2_p1",
                    instrucao = "Toque na conversa da Maria para abri-la.",
                    elementoAlvoId = "conversa_maria"
                ),
                Passo(
                    id = "wpp2_p2",
                    instrucao = "Toque no campo de texto na parte de baixo da tela.",
                    elementoAlvoId = "campo_mensagem"
                ),
                Passo(
                    id = "wpp2_p3",
                    instrucao = "Agora toque no botão de enviar (a setinha verde).",
                    elementoAlvoId = "btn_enviar_mensagem"
                )
            )
        ),

        // ------------------------------------------------------------------
        // Aula 3 — Enviar áudio
        // ------------------------------------------------------------------
        Aula(
            id = ID_AULA_3,
            appId = CatalogoApps.ID_WHATSAPP,
            ordem = 3,
            titulo = "Enviar um áudio",
            descricao = "Nesta aula você vai aprender a gravar e enviar " +
                    "uma mensagem de voz.",
            passos = listOf(
                Passo(
                    id = "wpp3_p1",
                    instrucao = "Toque na conversa da Maria para abri-la.",
                    elementoAlvoId = "conversa_maria"
                ),
                Passo(
                    id = "wpp3_p2",
                    instrucao = "Toque e segure o ícone do microfone para gravar.",
                    elementoAlvoId = "btn_microfone"
                ),
                Passo(
                    id = "wpp3_p3",
                    instrucao = "Solte o microfone para enviar o áudio.",
                    elementoAlvoId = "btn_enviar_audio"
                )
            )
        ),

        // ------------------------------------------------------------------
        // Aula 4 — Iniciar chamada de vídeo
        // ------------------------------------------------------------------
        Aula(
            id = ID_AULA_4,
            appId = CatalogoApps.ID_WHATSAPP,
            ordem = 4,
            titulo = "Fazer uma chamada de vídeo",
            descricao = "Nesta aula você vai aprender a iniciar " +
                    "uma chamada de vídeo com alguém.",
            passos = listOf(
                Passo(
                    id = "wpp4_p1",
                    instrucao = "Toque na conversa da Maria para abri-la.",
                    elementoAlvoId = "conversa_maria"
                ),
                Passo(
                    id = "wpp4_p2",
                    instrucao = "Toque no ícone de câmera no canto superior direito.",
                    elementoAlvoId = "btn_chamada_video"
                ),
                Passo(
                    id = "wpp4_p3",
                    instrucao = "Toque em 'Chamada de vídeo' para confirmar.",
                    elementoAlvoId = "btn_confirmar_video"
                )
            )
        ),

        // ------------------------------------------------------------------
        // Aula 5 — Inverter câmera durante chamada
        // ------------------------------------------------------------------
        Aula(
            id = ID_AULA_5,
            appId = CatalogoApps.ID_WHATSAPP,
            ordem = 5,
            titulo = "Inverter a câmera",
            descricao = "Nesta aula você vai aprender a trocar entre " +
                    "a câmera frontal e a traseira durante uma chamada de vídeo.",
            passos = listOf(
                Passo(
                    id = "wpp5_p1",
                    instrucao = "Toque na conversa da Maria para abri-la.",
                    elementoAlvoId = "conversa_maria"
                ),
                Passo(
                    id = "wpp5_p2",
                    instrucao = "Toque no ícone de câmera para iniciar a chamada de vídeo.",
                    elementoAlvoId = "btn_chamada_video"
                ),
                Passo(
                    id = "wpp5_p3",
                    instrucao = "Toque em 'Chamada de vídeo' para confirmar.",
                    elementoAlvoId = "btn_confirmar_video"
                ),
                Passo(
                    id = "wpp5_p4",
                    instrucao = "Agora toque no botão de inverter câmera.",
                    elementoAlvoId = "btn_inverter_camera"
                )
            )
        )
    )

    fun buscarPorId(id: String): Aula? = aulas.find { it.id == id }

    fun buscarPorApp(appId: String): List<Aula> =
        aulas.filter { it.appId == appId }.sortedBy { it.ordem }
}