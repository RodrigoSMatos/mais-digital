package com.maisdigital.app.data.catalog

import com.maisdigital.app.domain.model.Aula
import com.maisdigital.app.domain.model.Passo

/**
 * As 8 aulas do módulo WhatsApp.
 *
 * Estrutura pedagógica:
 *   1-3: Funções básicas (contatos, mensagem, áudio)
 *   4-7: Chamada de vídeo em profundidade (tela, câmera, áudio, layout)
 *   8:   Recursos avançados (menu e compartilhamento de tela)
 */
object CatalogoAulasWhatsApp {

    const val ID_AULA_1 = "wpp_aula_1"
    const val ID_AULA_2 = "wpp_aula_2"
    const val ID_AULA_3 = "wpp_aula_3"
    const val ID_AULA_4 = "wpp_aula_4"
    const val ID_AULA_5 = "wpp_aula_5"
    const val ID_AULA_6 = "wpp_aula_6"
    const val ID_AULA_7 = "wpp_aula_7"
    const val ID_AULA_8 = "wpp_aula_8"

    val aulas: List<Aula> = listOf(

        // ==================================================================
        // AULA 1 — Adicionar um contato
        // ==================================================================
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
                    instrucao = "Toque no botão verde de nova conversa, no canto inferior direito da tela.",
                    elementoAlvoId = "btn_nova_conversa"
                ),
                Passo(
                    id = "wpp1_p2",
                    instrucao = "Agora toque em 'Novo contato' para criar um novo contato.",
                    elementoAlvoId = "btn_novo_contato"
                ),
                Passo(
                    id = "wpp1_p3",
                    instrucao = "Toque no campo 'Nome' para digitar o nome da pessoa.",
                    elementoAlvoId = "campo_nome_contato"
                ),
                Passo(
                    id = "wpp1_p4",
                    instrucao = "Agora toque no campo 'Telefone' para digitar o número.",
                    elementoAlvoId = "campo_telefone_contato"
                ),
                Passo(
                    id = "wpp1_p5",
                    instrucao = "Toque em 'Salvar' para guardar o novo contato.",
                    elementoAlvoId = "btn_salvar_contato"
                )
            )
        ),

        // ==================================================================
        // AULA 2 — Enviar uma mensagem
        // ==================================================================
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

        // ==================================================================
        // AULA 3 — Enviar um áudio
        // ==================================================================
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

        // ==================================================================
        // AULA 4 — Conhecendo a chamada de vídeo
        // ==================================================================
        Aula(
            id = ID_AULA_4,
            appId = CatalogoApps.ID_WHATSAPP,
            ordem = 4,
            titulo = "Conhecendo a chamada de vídeo",
            descricao = "Nesta aula você vai aprender a iniciar uma chamada " +
                    "e a reconhecer cada parte da tela.",
            passos = listOf(
                Passo(
                    id = "wpp4_p1",
                    instrucao = "Toque na conversa da Maria para começar.",
                    elementoAlvoId = "conversa_maria"
                ),
                Passo(
                    id = "wpp4_p2",
                    instrucao = "Toque no botão de câmera para iniciar a chamada.",
                    elementoAlvoId = "btn_chamada_video"
                ),
                Passo(
                    id = "wpp4_p3",
                    instrucao = "Aqui aparece o nome da pessoa com quem você está falando. Toque para continuar.",
                    elementoAlvoId = "info_pessoa_chamada"
                ),
                Passo(
                    id = "wpp4_p4",
                    instrucao = "A imagem grande mostra a outra pessoa. Toque para continuar.",
                    elementoAlvoId = "area_principal_chamada"
                ),
                Passo(
                    id = "wpp4_p5",
                    instrucao = "A imagem pequena no canto mostra como você aparece. Toque para continuar.",
                    elementoAlvoId = "miniatura_propria_camera"
                ),
                Passo(
                    id = "wpp4_p6",
                    instrucao = "Pronto! Agora toque no botão vermelho para encerrar a chamada.",
                    elementoAlvoId = "btn_encerrar_chamada"
                )
            )
        ),

        // ==================================================================
        // AULA 5 — Controlar a câmera
        // ==================================================================
        Aula(
            id = ID_AULA_5,
            appId = CatalogoApps.ID_WHATSAPP,
            ordem = 5,
            titulo = "Controlar sua câmera",
            descricao = "Nesta aula você vai aprender a desligar e ligar sua câmera, " +
                    "e a trocar entre a câmera da frente e a de trás.",
            passos = listOf(
                Passo(
                    id = "wpp5_p1",
                    instrucao = "Toque na conversa da Maria para começar.",
                    elementoAlvoId = "conversa_maria"
                ),
                Passo(
                    id = "wpp5_p2",
                    instrucao = "Toque no botão de câmera para iniciar a chamada.",
                    elementoAlvoId = "btn_chamada_video"
                ),
                Passo(
                    id = "wpp5_p3",
                    instrucao = "Toque no botão de câmera para desligar sua câmera.",
                    elementoAlvoId = "btn_camera_chamada"
                ),
                Passo(
                    id = "wpp5_p4",
                    instrucao = "Pronto! Sua câmera foi desligada. Toque de novo para ligar.",
                    elementoAlvoId = "btn_camera_chamada"
                ),
                Passo(
                    id = "wpp5_p5",
                    instrucao = "Agora toque no botão de inverter câmera para usar a câmera de trás.",
                    elementoAlvoId = "btn_inverter_camera"
                ),
                Passo(
                    id = "wpp5_p6",
                    instrucao = "Toque novamente para voltar para a câmera da frente.",
                    elementoAlvoId = "btn_inverter_camera"
                ),
                Passo(
                    id = "wpp5_p7",
                    instrucao = "Pronto! Toque no botão vermelho para encerrar.",
                    elementoAlvoId = "btn_encerrar_chamada"
                )
            )
        ),

        // ==================================================================
        // AULA 6 — Controlar microfone e som
        // ==================================================================
        Aula(
            id = ID_AULA_6,
            appId = CatalogoApps.ID_WHATSAPP,
            ordem = 6,
            titulo = "Controlar o microfone e o som",
            descricao = "Nesta aula você vai aprender a silenciar seu microfone " +
                    "e a ativar o viva voz.",
            passos = listOf(
                Passo(
                    id = "wpp6_p1",
                    instrucao = "Toque na conversa da Maria para começar.",
                    elementoAlvoId = "conversa_maria"
                ),
                Passo(
                    id = "wpp6_p2",
                    instrucao = "Toque no botão de câmera para iniciar a chamada.",
                    elementoAlvoId = "btn_chamada_video"
                ),
                Passo(
                    id = "wpp6_p3",
                    instrucao = "Toque no botão de microfone para silenciar.",
                    elementoAlvoId = "btn_microfone_chamada"
                ),
                Passo(
                    id = "wpp6_p4",
                    instrucao = "Pronto! Agora a outra pessoa não ouve você. Toque de novo para falar.",
                    elementoAlvoId = "btn_microfone_chamada"
                ),
                Passo(
                    id = "wpp6_p5",
                    instrucao = "Toque no botão de alto-falante para ativar o viva voz.",
                    elementoAlvoId = "btn_alto_falante"
                ),
                Passo(
                    id = "wpp6_p6",
                    instrucao = "O som agora sai no alto-falante do celular. Toque para encerrar.",
                    elementoAlvoId = "btn_encerrar_chamada"
                )
            )
        ),

        // ==================================================================
        // AULA 7 — Expandir a imagem
        // ==================================================================
        Aula(
            id = ID_AULA_7,
            appId = CatalogoApps.ID_WHATSAPP,
            ordem = 7,
            titulo = "Expandir a imagem da chamada",
            descricao = "Nesta aula você vai aprender a alternar entre ver " +
                    "a outra pessoa em grande e ver você mesmo em grande.",
            passos = listOf(
                Passo(
                    id = "wpp7_p1",
                    instrucao = "Toque na conversa da Maria para começar.",
                    elementoAlvoId = "conversa_maria"
                ),
                Passo(
                    id = "wpp7_p2",
                    instrucao = "Toque no botão de câmera para iniciar a chamada.",
                    elementoAlvoId = "btn_chamada_video"
                ),
                Passo(
                    id = "wpp7_p3",
                    instrucao = "Toque na sua imagem pequena no canto para expandir.",
                    elementoAlvoId = "miniatura_propria_camera"
                ),
                Passo(
                    id = "wpp7_p4",
                    instrucao = "Agora você aparece em tamanho grande. Toque na imagem pequena para voltar.",
                    elementoAlvoId = "miniatura_outra_pessoa"
                ),
                Passo(
                    id = "wpp7_p5",
                    instrucao = "Pronto! Toque no botão vermelho para encerrar.",
                    elementoAlvoId = "btn_encerrar_chamada"
                )
            )
        ),

        // ==================================================================
        // AULA 8 — Menu de opções e compartilhar tela
        // ==================================================================
        Aula(
            id = ID_AULA_8,
            appId = CatalogoApps.ID_WHATSAPP,
            ordem = 8,
            titulo = "Menu e compartilhar tela",
            descricao = "Nesta aula você vai conhecer o menu de opções extras " +
                    "e aprender a compartilhar sua tela com a outra pessoa.",
            passos = listOf(
                Passo(
                    id = "wpp8_p1",
                    instrucao = "Toque na conversa da Maria para começar.",
                    elementoAlvoId = "conversa_maria"
                ),
                Passo(
                    id = "wpp8_p2",
                    instrucao = "Toque no botão de câmera para iniciar a chamada.",
                    elementoAlvoId = "btn_chamada_video"
                ),
                Passo(
                    id = "wpp8_p3",
                    instrucao = "Toque nos três pontinhos para abrir o menu de opções.",
                    elementoAlvoId = "btn_tres_pontinhos"
                ),
                Passo(
                    id = "wpp8_p4",
                    instrucao = "Aqui ficam opções extras. Toque em 'Compartilhar tela'.",
                    elementoAlvoId = "opcao_compartilhar_tela"
                ),
                Passo(
                    id = "wpp8_p5",
                    instrucao = "O celular vai pedir confirmação. Toque em 'Aceitar' para compartilhar.",
                    elementoAlvoId = "btn_aceitar_compartilhamento"
                ),
                Passo(
                    id = "wpp8_p6",
                    instrucao = "Pronto! Agora a outra pessoa vê sua tela. Toque no botão vermelho para encerrar.",
                    elementoAlvoId = "btn_encerrar_chamada"
                )
            )
        )
    )

    fun buscarPorId(id: String): Aula? = aulas.find { it.id == id }

    fun buscarPorApp(appId: String): List<Aula> =
        aulas.filter { it.appId == appId }.sortedBy { it.ordem }
}