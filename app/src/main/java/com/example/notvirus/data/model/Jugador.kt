package com.example.notvirus.data.model

import com.example.notvirus.data.model.Carta

class Jugador(
    var nombre: String = "Player X", // nombre de Usuario
    var isActive: Boolean = false,
    var mano: Mano = Mano(),
    var mesa: Mesa = Mesa(),
) {
    fun takeCartas(nuevasCartas: MutableList<Carta>): Unit {
        mano.addCartas(nuevasCartas = nuevasCartas)
    }

    fun discardCartas(): MutableList<Carta> {
        val cartasDescartardas = mano.takeSelectedCarta()
        mano.removeSelectedCartas()
        return cartasDescartardas
    }

    fun playCarta(): Carta {
        // mover una carta a la mesa
        val cartaJugada = mano.takeSelectedCarta()
        if (cartaJugada.size > 1) {
            throw Exception("Más de una carta seleccionada")
        }
        mano.removeSelectedCartas()
        return cartaJugada[0]
    }
}