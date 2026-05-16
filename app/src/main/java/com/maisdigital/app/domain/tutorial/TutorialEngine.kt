package com.maisdigital.app.domain.tutorial

import com.maisdigital.app.domain.model.Aula
import com.maisdigital.app.domain.model.TutorialState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Motor do tutorial guiado do +Digital.
 *
 * Responsabilidades:
 * - Controlar qual passo está ativo.
 * - Validar se o elemento clicado é o alvo correto.
 * - Emitir feedback de erro amigável quando o clique é errado.
 * - Avançar para o próximo passo quando o clique é correto.
 * - Sinalizar conclusão da aula.
 *
 * É Kotlin puro — sem dependência de Android ou Compose.
 * Pode ser testado com JUnit simples.
 *
 * Uso:
 *   val engine = TutorialEngine(mensagensErro)
 *   engine.iniciar(aula)
 *   engine.state.collect { state -> /* atualiza UI */ }
 *   engine.aoClicar("conversa_pedro")
 */
class TutorialEngine(
    private val mensagensErro: List<String> = listOf(
        "Quase! Toque na área destacada.",
        "Você está perto. Siga a marcação na tela.",
        "Tente tocar no botão indicado.",
        "Olhe a área em destaque e tente novamente."
    )
) {
    private lateinit var aulaAtual: Aula
    private var indicePasso: Int = 0
    private var erroCount: Int = 0

    private val _state = MutableStateFlow<TutorialState?>(null)

    /** StateFlow observado pela UI. Null enquanto nenhuma aula foi iniciada. */
    val state: StateFlow<TutorialState?> = _state.asStateFlow()

    // -------------------------------------------------------------------------
    // API pública
    // -------------------------------------------------------------------------

    /**
     * Inicia uma aula do zero.
     * Pode ser chamado novamente para repetir a aula.
     */
    fun iniciar(aula: Aula) {
        aulaAtual = aula
        indicePasso = 0
        erroCount = 0
        emitirEstadoAtual()
    }

    /**
     * Chamado quando o usuário toca em um elemento identificado.
     * O Modifier.alvoTutorial() chama este método automaticamente.
     */
    fun aoClicar(elementoId: String) {
        val state = _state.value ?: return
        if (state.concluida) return

        val passoAtual = aulaAtual.passos[indicePasso]

        if (elementoId == passoAtual.elementoAlvoId) {
            acertou()
        } else {
            errou()
        }
    }

    /**
     * Chamado quando o usuário toca fora de qualquer elemento marcado.
     * Conta como clique errado.
     */
    fun aoClicarFora() {
        val state = _state.value ?: return
        if (state.concluida) return
        errou()
    }

    // -------------------------------------------------------------------------
    // Lógica interna
    // -------------------------------------------------------------------------

    private fun acertou() {
        val ehUltimoPasso = indicePasso >= aulaAtual.passos.lastIndex

        if (ehUltimoPasso) {
            _state.value = construirState().copy(concluida = true)
        } else {
            indicePasso++
            erroCount = 0
            emitirEstadoAtual()
        }
    }

    private fun errou() {
        val mensagem = mensagensErro[erroCount % mensagensErro.size]
        erroCount++
        _state.value = construirState().copy(erro = mensagem)
    }

    private fun emitirEstadoAtual() {
        _state.value = construirState()
    }

    private fun construirState(): TutorialState {
        val passo = aulaAtual.passos[indicePasso]
        return TutorialState(
            aula = aulaAtual,
            indicePasso = indicePasso,
            totalPassos = aulaAtual.passos.size,
            instrucaoAtual = passo.instrucao,
            elementoAlvoId = passo.elementoAlvoId,
            erro = null,
            concluida = false
        )
    }

    /**
     * Limpa a mensagem de erro atual sem alterar o passo.
     * Chamado pelo ViewModel após um timeout ou quando o usuário acerta.
     */
    fun limparErro() {
        val state = _state.value ?: return
        if (state.erro != null) {
            _state.value = state.copy(erro = null)
        }
    }
}