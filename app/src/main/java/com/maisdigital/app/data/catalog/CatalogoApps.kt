package com.maisdigital.app.data.catalog

import com.maisdigital.app.domain.model.AppSimulado

/**
 * Lista de apps disponíveis no +Digital.
 * Para adicionar um novo app no futuro: adicione um item aqui
 * e crie a pasta feature/simuladores/novoapp/.
 */
object CatalogoApps {

    const val ID_WHATSAPP = "whatsapp"
    const val ID_GMAIL = "gmail"
    const val ID_MAPS = "maps"

    val apps: List<AppSimulado> = listOf(
        AppSimulado(
            id = ID_WHATSAPP,
            nome = "WhatsApp",
            descricao = "Aprenda a enviar mensagens, áudios e fazer chamadas de vídeo.",
            disponivel = true
        ),
        AppSimulado(
            id = ID_GMAIL,
            nome = "Gmail",
            descricao = "Aprenda a ler e enviar e-mails.",
            disponivel = false
        ),
        AppSimulado(
            id = ID_MAPS,
            nome = "Google Maps",
            descricao = "Aprenda a encontrar lugares e traçar rotas.",
            disponivel = false
        )
    )

    fun buscarPorId(id: String): AppSimulado? = apps.find { it.id == id }
}