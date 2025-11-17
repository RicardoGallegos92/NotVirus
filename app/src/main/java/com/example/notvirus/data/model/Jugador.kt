package com.example.notvirus.data.model

import com.example.notvirus.data.model.Carta

class Jugador(
    var nombre: String = "Player X", // nombre de Usuario
    var isActive: Boolean = false,
    var mano: Mano,
    var mesa: Mesa,
) {
    fun addCartas(nuevasCartas: MutableList<Carta>): Unit {
        mano.addCarta(nuevasCartas = nuevasCartas)
    }

    fun discardCartas(): MutableList<Carta> {
        var cartasDescartardas = mano.takeSelectedCarta()
        mano.removeSelectedCartas()
        return cartasDescartardas
    }

    fun playCarta(): Carta {
        // mover una carta a la mesa
        var cartaJugada = mano.takeSelectedCarta()
        if (cartaJugada.size > 1) {
            throw Exception("Más de una carta seleccionada")
        }
        mano.removeSelectedCartas()
        return cartaJugada[0]
    }
}