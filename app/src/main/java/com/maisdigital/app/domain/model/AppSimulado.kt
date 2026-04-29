package com.maisdigital.app.domain.model

/**
 * Representa um app que o usuário pode aprender no +Digital.
 * Ex: WhatsApp, Gmail, etc.
 *
 * [disponivel] controla se aparece como clicável ou "Em breve" no menu.
 */
data class AppSimulado(
    val id: String,
    val nome: String,
    val descricao: String,
    val disponivel: Boolean = false
)