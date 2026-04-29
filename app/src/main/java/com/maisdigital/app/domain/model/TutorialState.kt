package com.maisdigital.app.domain.model

/**
 * Estado completo do tutorial em um dado momento.
 * É um snapshot imutável — o TutorialEngine emite um novo a cada mudança.
 *
 * [aula]              → aula em execução.
 * [indicePasso]       → índice do passo atual (0-based).
 * [totalPassos]       → total de passos da aula.
 * [instrucaoAtual]    → texto a ser exibido.
 * [elementoAlvoId]    → id do elemento que deve ser tocado agora.
 * [erro]              → mensagem de erro amigável, ou null se não houve erro.
 * [concluida]         → true quando o último passo foi completado.
 */
data class TutorialState(
    val aula: Aula,
    val indicePasso: Int = 0,
    val totalPassos: Int = 0,
    val instrucaoAtual: String = "",
    val elementoAlvoId: String = "",
    val erro: String? = null,
    val concluida: Boolean = false
) {
    /** Progresso de 0.0 a 1.0 para exibir barra de progresso. */
    val progresso: Float
        get() = if (totalPassos == 0) 0f
        else (indicePasso + 1).toFloat() / totalPassos.toFloat()
}