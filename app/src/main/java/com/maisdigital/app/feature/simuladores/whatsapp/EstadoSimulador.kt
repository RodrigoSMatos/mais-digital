package com.maisdigital.app.feature.simuladores.whatsapp

/**
 * Telas internas do simulador WhatsApp.
 */
sealed class EstadoSimulador {
    data object ListaConversas : EstadoSimulador()
    data object Contatos : EstadoSimulador()       // novo
    data object NovoContato : EstadoSimulador()
    data object Conversa : EstadoSimulador()
    data object DialogChamadaVideo : EstadoSimulador()
    data object ChamadaVideoAtiva : EstadoSimulador()
}