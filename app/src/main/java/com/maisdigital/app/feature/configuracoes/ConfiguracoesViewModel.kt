package com.maisdigital.app.feature.configuracoes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.maisdigital.app.MaisDigitalApp
import com.maisdigital.app.domain.model.TamanhoTexto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConfiguracoesViewModel(application: Application) : AndroidViewModel(application) {

    private val configRepository = (application as MaisDigitalApp).configRepository

    val tamanhoTexto: StateFlow<TamanhoTexto> = configRepository.tamanhoTexto
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TamanhoTexto.PADRAO
        )

    fun setTamanhoTexto(novo: TamanhoTexto) {
        viewModelScope.launch {
            configRepository.setTamanhoTexto(novo)
        }
    }
}