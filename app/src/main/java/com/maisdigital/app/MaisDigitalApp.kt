package com.maisdigital.app

import android.app.Application

/**
 * Application class do +Digital.
 * Atualmente vazia. Será expandida na Etapa 6 para inicializar o DataStore
 * e injetar dependências no nível do app.
 */
class MaisDigitalApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializações futuras: DataStore, container de dependências, crash reporter, etc.
    }
}