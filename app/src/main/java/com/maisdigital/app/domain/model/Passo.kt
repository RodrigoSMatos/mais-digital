package com.maisdigital.app.domain.model

/**
 * Um passo dentro de uma aula.
 *
 * [instrucao]          → texto exibido ao usuário. Ex: "Toque na conversa do Pedro."
 * [elementoAlvoId]     → id do elemento que deve ser tocado para avançar.
 *                        Deve corresponder ao id passado em Modifier.alvoTutorial().
 * [elementoFeedbackId] → id do elemento onde o RESULTADO da ação aparece, quando
 *                        for diferente do alvo. Ex: no passo "toque em inverter
 *                        câmera", o alvo é o botão na barra inferior, mas o
 *                        feedback aparece na miniatura. Quando null (padrão),
 *                        assume-se que o feedback acontece no próprio alvo.
 *                        Usado pelo CartaoInstrucao para não cobrir o feedback.
 * [audioInstrucao]     → reservado para narração futura (nullable = ignorado no MVP).
 */
data class Passo(
    val id: String,
    val instrucao: String,
    val elementoAlvoId: String,
    val elementoFeedbackId: String? = null,
    val audioInstrucao: String? = null
)