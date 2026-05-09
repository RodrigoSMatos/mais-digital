package com.maisdigital.app.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.maisdigital.app.data.local.preferenciasDataStore
import com.maisdigital.app.domain.model.TamanhoTexto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Configurações gerais do app: tamanho de fonte, etc.
 */
class ConfigRepository(private val context: Context) {

    companion object {
        private val KEY_TAMANHO_TEXTO = stringPreferencesKey("tamanho_texto")
    }

    /** Flow do tamanho de texto preferido. Default: MEDIO. */
    val tamanhoTexto: Flow<TamanhoTexto> = context.preferenciasDataStore.data
        .map { prefs -> TamanhoTexto.fromNome(prefs[KEY_TAMANHO_TEXTO]) }

    suspend fun setTamanhoTexto(tamanho: TamanhoTexto) {
        context.preferenciasDataStore.edit { prefs ->
            prefs[KEY_TAMANHO_TEXTO] = tamanho.name
        }
    }
}