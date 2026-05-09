package com.maisdigital.app

import android.app.Application
import com.maisdigital.app.data.repository.ConfigRepository
import com.maisdigital.app.data.repository.ProgressoRepository

/**
 * Application class do +Digital.
 *
 * Faz o papel de Service Locator simples — instâncias únicas dos repositórios
 * que vivem o tempo todo do processo. Sem framework de DI no MVP.
 *
 * ViewModels acessam via (application as MaisDigitalApp).progressoRepository.
 */
class MaisDigitalApp : Application() {

    lateinit var progressoRepository: ProgressoRepository
        private set

    lateinit var configRepository: ConfigRepository
        private set

    override fun onCreate() {
        super.onCreate()
        progressoRepository = ProgressoRepository(this)
        configRepository = ConfigRepository(this)
    }
}