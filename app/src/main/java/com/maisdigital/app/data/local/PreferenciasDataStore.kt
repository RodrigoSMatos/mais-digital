package com.maisdigital.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * Extensão do Context que expõe o DataStore Preferences do app.
 *
 * Usar via Context.preferenciasDataStore (de qualquer lugar com Context).
 * O DataStore é singleton por arquivo — o nome "+digital_prefs" garante isso.
 */
val Context.preferenciasDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "mais_digital_prefs"
)