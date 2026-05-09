package com.maisdigital.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.maisdigital.app.feature.configuracoes.ConfiguracoesScreen
import com.maisdigital.app.feature.introaula.IntroAulaScreen
import com.maisdigital.app.feature.listaaulas.ListaAulasScreen
import com.maisdigital.app.feature.menuapps.MenuAppsScreen
import com.maisdigital.app.feature.parabens.ParabensScreen
import com.maisdigital.app.feature.splash.SplashScreen
import com.maisdigital.app.feature.tutorial.TutorialScreen

/**
 * Grafo de navegação do app.
 * Cada tela é responsável por chamar os callbacks de navegação que recebe.
 * As telas não conhecem o NavController — isso facilita testes e previews.
 */
@Composable
fun NavegacaoMaisDigital() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rotas.Splash.rota
    ) {
        composable(Rotas.Splash.rota) {
            SplashScreen(
                aoFinalizar = {
                    navController.navigate(Rotas.MenuApps.rota) {
                        popUpTo(Rotas.Splash.rota) { inclusive = true }
                    }
                }
            )
        }

        composable(Rotas.MenuApps.rota) {
            MenuAppsScreen(
                aoSelecionarApp = { appId ->
                    navController.navigate(Rotas.ListaAulas.criar(appId))
                },
                aoAbrirConfiguracoes = {
                    navController.navigate(Rotas.Configuracoes.rota)
                }
            )
        }

        composable(
            route = Rotas.ListaAulas.rota,
            arguments = listOf(
                navArgument(Rotas.ListaAulas.ARG_APP_ID) { type = NavType.StringType }
            )
        ) { entry ->
            val appId = entry.arguments?.getString(Rotas.ListaAulas.ARG_APP_ID).orEmpty()
            ListaAulasScreen(
                appId = appId,
                aoVoltar = { navController.popBackStack() },
                aoSelecionarAula = { aulaId ->
                    navController.navigate(Rotas.IntroAula.criar(appId, aulaId))
                }
            )
        }

        composable(
            route = Rotas.IntroAula.rota,
            arguments = listOf(
                navArgument(Rotas.IntroAula.ARG_APP_ID) { type = NavType.StringType },
                navArgument(Rotas.IntroAula.ARG_AULA_ID) { type = NavType.StringType }
            )
        ) { entry ->
            val appId = entry.arguments?.getString(Rotas.IntroAula.ARG_APP_ID).orEmpty()
            val aulaId = entry.arguments?.getString(Rotas.IntroAula.ARG_AULA_ID).orEmpty()
            IntroAulaScreen(
                appId = appId,
                aulaId = aulaId,
                aoVoltar = { navController.popBackStack() },
                aoComecar = {
                    navController.navigate(Rotas.Tutorial.criar(appId, aulaId)) {
                        popUpTo(Rotas.IntroAula.rota) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Rotas.Tutorial.rota,
            arguments = listOf(
                navArgument(Rotas.Tutorial.ARG_APP_ID) { type = NavType.StringType },
                navArgument(Rotas.Tutorial.ARG_AULA_ID) { type = NavType.StringType }
            )
        ) { entry ->
            val appId = entry.arguments?.getString(Rotas.Tutorial.ARG_APP_ID).orEmpty()
            val aulaId = entry.arguments?.getString(Rotas.Tutorial.ARG_AULA_ID).orEmpty()
            TutorialScreen(
                appId = appId,
                aulaId = aulaId,
                aoSair = {
                    navController.popBackStack(Rotas.ListaAulas.rota, inclusive = false)
                },
                aoConcluir = {
                    navController.navigate(Rotas.Parabens.criar(appId, aulaId)) {
                        popUpTo(Rotas.Tutorial.rota) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Rotas.Parabens.rota,
            arguments = listOf(
                navArgument(Rotas.Parabens.ARG_APP_ID) { type = NavType.StringType },
                navArgument(Rotas.Parabens.ARG_AULA_ID) { type = NavType.StringType }
            )
        ) { entry ->
            val appId = entry.arguments?.getString(Rotas.Parabens.ARG_APP_ID).orEmpty()
            val aulaId = entry.arguments?.getString(Rotas.Parabens.ARG_AULA_ID).orEmpty()
            ParabensScreen(
                appId = appId,
                aulaId = aulaId,
                aoRepetir = {
                    navController.navigate(Rotas.Tutorial.criar(appId, aulaId)) {
                        popUpTo(Rotas.Parabens.rota) { inclusive = true }
                    }
                },
                aoVoltarLista = {
                    navController.popBackStack(Rotas.ListaAulas.rota, inclusive = false)
                },
                aoVoltarMenu = {
                    navController.popBackStack(Rotas.MenuApps.rota, inclusive = false)
                }
            )
        }

        composable(Rotas.Configuracoes.rota) {
            ConfiguracoesScreen(
                aoVoltar = { navController.popBackStack() }
            )
        }

    }
}