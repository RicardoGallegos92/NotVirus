package com.example.notvirus.data.model

import androidx.collection.MutableObjectList

class Baraja(
    var mazo: MutableList<Carta> = mutableListOf(
        Carta(tipo = CartaTipo.ORGANO, color = CartaColor.VERDE)
    ),
) {
    private fun shuffle(): Unit {
        // random sort
    }

    // @params n => cantidad de cartas pedidas
    fun takeCartas(n: Int): MutableList <Carta> {
        val cartas = mazo.subList(0, n - 1)
        return cartas
    }
}