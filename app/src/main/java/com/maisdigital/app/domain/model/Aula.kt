package com.maisdigital.app.domain.model

/**
 * Uma aula completa dentro de un módulo de app.
 *
 * [appId]       → liga a aula ao AppSimulado correspondente.
 * [ordem]       → posição na lista de aulas (1, 2, 3...).
 * [titulo]      → nome curto. Ex: "Enviar mensagem".
 * [descricao]   → texto da tela de introdução.
 * [passos]      → sequência ordenada de Passos.
 */
data class Aula(
    val id: String,
    val appId: String,
    val ordem: Int,
    val titulo: String,
    val descricao: String,
    val passos: List<Passo>
)