package com.maisdigital.app.feature.listaaulas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.maisdigital.app.MaisDigitalApp
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope

class ListaAulasViewModel(application: Application) : AndroidViewModel(application) {

    private val progressoRepository = (application as MaisDigitalApp).progressoRepository

    val aulasConcluidas: StateFlow<Set<String>> = progressoRepository.aulasConcluidas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )
}