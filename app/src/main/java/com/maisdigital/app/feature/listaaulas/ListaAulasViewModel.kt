package com.maisdigital.app.feature.listaaulas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.maisdigital.app.MaisDigitalApp
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListaAulasViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as MaisDigitalApp
    private val progressoRepository = app.progressoRepository
    private val configRepository = app.configRepository

    val aulasConcluidas: StateFlow<Set<String>> = progressoRepository.aulasConcluidas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    val avisoSimulacaoVisto: StateFlow<Boolean> = configRepository.avisoSimulacaoVisto
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true // assume "visto" pra não piscar diálogo na 1a renderização
        )

    fun marcarAvisoVisto() {
        viewModelScope.launch {
            configRepository.marcarAvisoSimulacaoVisto()
        }
    }
}