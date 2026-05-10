package com.maisdigital.app.feature.tutorial

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.maisdigital.app.MaisDigitalApp
import com.maisdigital.app.data.catalog.CatalogoApps
import com.maisdigital.app.data.catalog.CatalogoAulasWhatsApp
import com.maisdigital.app.domain.model.Aula
import com.maisdigital.app.domain.tutorial.RegistroAlvos
import com.maisdigital.app.domain.tutorial.TutorialEngine
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TutorialViewModel(application: Application) : AndroidViewModel(application) {

    val engine = TutorialEngine()
    val registro = RegistroAlvos()

    private val _shakeTrigger = MutableStateFlow(0)
    val shakeTrigger: StateFlow<Int> = _shakeTrigger.asStateFlow()

    /**
     * Indica se o tutorial está pronto para receber cliques.
     * Inicia em false e vira true após um pequeno delay,
     * evitando que o tap residual de "Começar aula" caia aqui.
     */
    private val _aceitaCliques = MutableStateFlow(false)
    val aceitaCliques: StateFlow<Boolean> = _aceitaCliques.asStateFlow()

    private val progressoRepository = (application as MaisDigitalApp).progressoRepository

    private var aulaCarregada: Aula? = null

    init {
        observarEstado()
    }

    fun carregarAula(appId: String, aulaId: String) {
        if (aulaCarregada?.id == aulaId) return

        val aula = when (appId) {
            CatalogoApps.ID_WHATSAPP -> CatalogoAulasWhatsApp.buscarPorId(aulaId)
            else -> null
        } ?: return

        aulaCarregada = aula
        engine.iniciar(aula)

        // Bloqueia cliques por 500ms para descartar tap residual
        _aceitaCliques.value = false
        viewModelScope.launch {
            delay(800)   // antes era 500
            _aceitaCliques.value = true
        }
    }

    fun reiniciar() {
        aulaCarregada?.let {
            engine.iniciar(it)
            _aceitaCliques.value = false
            viewModelScope.launch {
                delay(500)
                _aceitaCliques.value = true
            }
        }
    }

    private fun observarEstado() {
        viewModelScope.launch {
            var ultimaMensagemErro: String? = null
            var aulaJaSalva: String? = null

            engine.state.collect { state ->
                if (state == null) return@collect

                val erro = state.erro
                if (erro != null && erro != ultimaMensagemErro) {
                    ultimaMensagemErro = erro
                    _shakeTrigger.value = _shakeTrigger.value + 1
                    launch {
                        delay(2500)
                        if (engine.state.value?.erro == erro) {
                            engine.limparErro()
                        }
                    }
                } else if (erro == null) {
                    ultimaMensagemErro = null
                }

                if (state.concluida && aulaJaSalva != state.aula.id) {
                    aulaJaSalva = state.aula.id
                    progressoRepository.marcarConcluida(state.aula.id)
                }
            }
        }
    }
}