package com.maisdigital.app.domain.model

/**
 * Tamanho de texto preferido pelo usuário.
 * Cada opção tem um multiplicador aplicado na tipografia base.
 *
 * Pequeno  = tipografia padrão
 * Medio    = +15% (recomendado para idosos)
 * Grande   = +30% (para baixa visão)
 */
enum class TamanhoTexto(val multiplicador: Float, val nomeExibicao: String) {
    PEQUENO(1.0f, "Pequeno"),
    MEDIO(1.15f, "Médio"),
    GRANDE(1.30f, "Grande");

    companion object {
        val PADRAO = MEDIO

        fun fromNome(nome: String?): TamanhoTexto =
            values().firstOrNull { it.name == nome } ?: PADRAO
    }
}