package com.maisdigital.app.domain.model

/**
 * Um passo dentro de uma aula.
 *
 * [instrucao]     → texto exibido ao usuário. Ex: "Toque na conversa da Maria."
 * [elementoAlvoId]→ id do elemento que deve ser tocado para avançar.
 *                   Deve corresponder ao id passado em Modifier.alvoTutorial().
 * [audioInstrucao]→ reservado para narração futura (nullable = ignorado no MVP).
 */
data class Passo(
    val id: String,
    val instrucao: String,
    val elementoAlvoId: String,
    val audioInstrucao: String? = null
)