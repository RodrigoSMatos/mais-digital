package com.maisdigital.app.domain.tutorial

import com.maisdigital.app.domain.model.Aula
import com.maisdigital.app.domain.model.Passo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TutorialEngineTest {

    private lateinit var engine: TutorialEngine
    private lateinit var aulaFake: Aula

    @Before
    fun setup() {
        engine = TutorialEngine(
            mensagensErro = listOf("Tente novamente.", "Quase lá.")
        )
        aulaFake = Aula(
            id = "aula_teste",
            appId = "whatsapp",
            ordem = 1,
            titulo = "Aula de Teste",
            descricao = "Descrição de teste",
            passos = listOf(
                Passo("p1", "Toque no botão A", "btn_a"),
                Passo("p2", "Toque no botão B", "btn_b"),
                Passo("p3", "Toque no botão C", "btn_c")
            )
        )
    }

    @Test
    fun `iniciar aula define estado no primeiro passo`() = runTest {
        engine.iniciar(aulaFake)
        val state = engine.state.first()!!

        assertEquals(0, state.indicePasso)
        assertEquals(3, state.totalPassos)
        assertEquals("btn_a", state.elementoAlvoId)
        assertFalse(state.concluida)
        assertNull(state.erro)
    }

    @Test
    fun `clique correto avanca para proximo passo`() = runTest {
        engine.iniciar(aulaFake)
        engine.aoClicar("btn_a")
        val state = engine.state.first()!!

        assertEquals(1, state.indicePasso)
        assertEquals("btn_b", state.elementoAlvoId)
        assertNull(state.erro)
    }

    @Test
    fun `clique errado nao avanca e emite mensagem de erro`() = runTest {
        engine.iniciar(aulaFake)
        engine.aoClicar("btn_errado")
        val state = engine.state.first()!!

        assertEquals(0, state.indicePasso)
        assertNotNull(state.erro)
    }

    @Test
    fun `clique fora conta como erro`() = runTest {
        engine.iniciar(aulaFake)
        engine.aoClicarFora()
        val state = engine.state.first()!!

        assertEquals(0, state.indicePasso)
        assertNotNull(state.erro)
    }

    @Test
    fun `completar todos os passos marca aula como concluida`() = runTest {
        engine.iniciar(aulaFake)
        engine.aoClicar("btn_a")
        engine.aoClicar("btn_b")
        engine.aoClicar("btn_c")
        val state = engine.state.first()!!

        assertTrue(state.concluida)
    }

    @Test
    fun `progresso aumenta a cada passo correto`() = runTest {
        engine.iniciar(aulaFake)

        var state = engine.state.first()!!
        assertEquals(1f / 3f, state.progresso, 0.01f)

        engine.aoClicar("btn_a")
        state = engine.state.first()!!
        assertEquals(2f / 3f, state.progresso, 0.01f)

        engine.aoClicar("btn_b")
        state = engine.state.first()!!
        assertEquals(3f / 3f, state.progresso, 0.01f)
    }

    @Test
    fun `reiniciar aula reseta estado`() = runTest {
        engine.iniciar(aulaFake)
        engine.aoClicar("btn_a")
        engine.aoClicar("btn_b")

        engine.iniciar(aulaFake)
        val state = engine.state.first()!!

        assertEquals(0, state.indicePasso)
        assertFalse(state.concluida)
    }

    @Test
    fun `mensagens de erro alternam entre as disponiveis`() = runTest {
        engine.iniciar(aulaFake)

        engine.aoClicarFora()
        val erro1 = engine.state.first()!!.erro

        engine.aoClicarFora()
        val erro2 = engine.state.first()!!.erro

        assertEquals("Tente novamente.", erro1)
        assertEquals("Quase lá.", erro2)
    }
}
