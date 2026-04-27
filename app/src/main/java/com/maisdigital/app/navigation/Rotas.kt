package com.maisdigital.app.navigation

/**
 * Rotas de navegação do app.
 * Usar sealed class garante checagem em tempo de compilação.
 *
 * Algumas rotas recebem parâmetros (appId, aulaId). A construção da string
 * é encapsulada em funções para evitar erros de digitação.
 */
sealed class `Rotas.kt`(val rota: String) {

    data object Splash : `Rotas.kt`("splash")

    data object MenuApps : `Rotas.kt`("menu_apps")

    data object ListaAulas : `Rotas.kt`("lista_aulas/{appId}") {
        fun criar(appId: String) = "lista_aulas/$appId"
        const val ARG_APP_ID = "appId"
    }

    data object IntroAula : `Rotas.kt`("intro_aula/{appId}/{aulaId}") {
        fun criar(appId: String, aulaId: String) = "intro_aula/$appId/$aulaId"
        const val ARG_APP_ID = "appId"
        const val ARG_AULA_ID = "aulaId"
    }

    data object Tutorial : `Rotas.kt`("tutorial/{appId}/{aulaId}") {
        fun criar(appId: String, aulaId: String) = "tutorial/$appId/$aulaId"
        const val ARG_APP_ID = "appId"
        const val ARG_AULA_ID = "aulaId"
    }

    data object Parabens : `Rotas.kt`("parabens/{appId}/{aulaId}") {
        fun criar(appId: String, aulaId: String) = "parabens/$appId/$aulaId"
        const val ARG_APP_ID = "appId"
        const val ARG_AULA_ID = "aulaId"
    }
}