package com.maisdigital.app.feature.simuladores.whatsapp

/**
 * Telas internas do simulador WhatsApp.
 * O usuário "navega" entre elas tocando nos alvos do tutorial.
 *
 * IMPORTANTE: essa navegação interna NÃO usa NavController do Android.
 * É tudo controlado por estado pra ficar leve e testável.
 */
sealed class EstadoSimulador {
    data object ListaConversas : EstadoSimulador()
    data object NovoContato : EstadoSimulador()
    data object Conversa : EstadoSimulador()
    data object DialogChamadaVideo : EstadoSimulador()
    data object ChamadaVideoAtiva : EstadoSimulador()
}