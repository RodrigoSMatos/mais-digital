package com.maisdigital.app.domain.tutorial

import androidx.compose.ui.geometry.Rect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Mantém o mapa { elementoId -> retângulo na tela } dos elementos
 * registrados via Modifier.alvoTutorial().
 *
 * Atualiza-se em tempo real conforme elementos aparecem, mudam de
 * tamanho ou são removidos da composição.
 *
 * O overlay visual lê esse StateFlow para saber onde desenhar o spotlight.
 */
class RegistroAlvos {

    private val _alvos = MutableStateFlow<Map<String, Rect>>(emptyMap())
    val alvos: StateFlow<Map<String, Rect>> = _alvos.asStateFlow()

    fun registrar(id: String, rect: Rect) {
        _alvos.update { mapa -> mapa + (id to rect) }
    }

    fun desregistrar(id: String) {
        _alvos.update { mapa -> mapa - id }
    }

    fun rectDe(id: String): Rect? = _alvos.value[id]

    fun limpar() {
        _alvos.value = emptyMap()
    }
}