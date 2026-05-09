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

/**
 * ViewModel da tela de Tutorial.
 *
 * Diferente do anterior: agora é AndroidViewModel pra ter acesso ao Application,
 * de onde pega os repositórios. Quando a aula é concluída, marca no DataStore.
 */
class TutorialViewModel(application: Application) : AndroidViewModel(application) {

    val engine = TutorialEngine()
    val registro = RegistroAlvos()

    private val _shakeTrigger = MutableStateFlow(0)
    val shakeTrigger: StateFlow<Int> = _shakeTrigger.asStateFlow()

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
    }

    fun reiniciar() {
        aulaCarregada?.let { engine.iniciar(it) }
    }

    /**
     * Observa o engine para:
     *  - disparar shake quando há erro novo;
     *  - auto-limpar mensagens de erro após 2.5s;
     *  - salvar conclusão da aula no DataStore.
     */
    private fun observarEstado() {
        viewModelScope.launch {
            var ultimaMensagemErro: String? = null
            var aulaJaSalva: String? = null

            engine.state.collect { state ->
                if (state == null) return@collect

                // Erros
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

                // Conclusão — salva uma única vez
                if (state.concluida && aulaJaSalva != state.aula.id) {
                    aulaJaSalva = state.aula.id
                    progressoRepository.marcarConcluida(state.aula.id)
                }
            }
        }
    }
}