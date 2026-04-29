package com.maisdigital.app.feature.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

/**
 * ViewModel da tela de Tutorial.
 *
 * Responsabilidades:
 *  - Carregar a Aula a partir do appId/aulaId.
 *  - Manter instâncias do TutorialEngine e do RegistroAlvos.
 *  - Auto-limpar mensagens de erro após 2.5s.
 *  - Disparar trigger de shake para a tela animar.
 */
class TutorialViewModel : ViewModel() {

    val engine = TutorialEngine()
    val registro = RegistroAlvos()

    private val _shakeTrigger = MutableStateFlow(0)
    val shakeTrigger: StateFlow<Int> = _shakeTrigger.asStateFlow()

    private var aulaCarregada: Aula? = null

    fun carregarAula(appId: String, aulaId: String) {
        if (aulaCarregada?.id == aulaId) return

        val aula = when (appId) {
            CatalogoApps.ID_WHATSAPP -> CatalogoAulasWhatsApp.buscarPorId(aulaId)
            else -> null
        } ?: return

        aulaCarregada = aula
        engine.iniciar(aula)

        // Observa o engine para reagir a erros (shake + auto-limpar)
        viewModelScope.launch {
            var ultimaMensagemErro: String? = null
            engine.state.collect { state ->
                if (state == null) return@collect
                val erro = state.erro
                if (erro != null && erro != ultimaMensagemErro) {
                    ultimaMensagemErro = erro
                    _shakeTrigger.value = _shakeTrigger.value + 1
                    // Auto-limpa após 2.5s
                    launch {
                        delay(2500)
                        if (engine.state.value?.erro == erro) {
                            // Reemite estado sem erro
                            limparErro()
                        }
                    }
                } else if (erro == null) {
                    ultimaMensagemErro = null
                }
            }
        }
    }

    private fun limparErro() {
        // Hack simples: clica num id inexistente NÃO funcionaria (geraria erro de novo).
        // Em vez disso, expomos um método no engine. Vamos adicionar.
        engine.limparErro()
    }

    fun reiniciar() {
        aulaCarregada?.let { engine.iniciar(it) }
    }
}