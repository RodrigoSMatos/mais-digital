package com.maisdigital.app.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.maisdigital.app.data.local.preferenciasDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Gerencia o progresso do usuário: quais aulas foram concluídas.
 *
 * Armazenamento: DataStore Preferences, como Set<String> de aulaIds.
 * O Flow garante que a UI atualiza automaticamente quando algo muda.
 */
class ProgressoRepository(private val context: Context) {

    companion object {
        private val KEY_AULAS_CONCLUIDAS = stringSetPreferencesKey("aulas_concluidas")
    }

    /** Flow que emite o conjunto de IDs de aulas concluídas. */
    val aulasConcluidas: Flow<Set<String>> = context.preferenciasDataStore.data
        .map { prefs -> prefs[KEY_AULAS_CONCLUIDAS] ?: emptySet() }

    /** Marca uma aula como concluída (idempotente). */
    suspend fun marcarConcluida(aulaId: String) {
        context.preferenciasDataStore.edit { prefs ->
            val atuais = prefs[KEY_AULAS_CONCLUIDAS] ?: emptySet()
            prefs[KEY_AULAS_CONCLUIDAS] = atuais + aulaId
        }
    }

    /** Limpa todo o progresso (útil pra "começar de novo" em versão futura). */
    suspend fun limparProgresso() {
        context.preferenciasDataStore.edit { prefs ->
            prefs.remove(KEY_AULAS_CONCLUIDAS)
        }
    }
}