package com.example.notvirus.data.model

class PilaDescarte(
    var pila: MutableList<Carta> = mutableListOf(),
) {
    fun addCarta(cartasDescartadas: MutableList<Carta>): Unit {
        pila.addAll( cartasDescartadas)
    }

    fun empty(): MutableList<Carta> {
        var cartas: MutableList<Carta> = pila.toMutableList()
        pila.clear()
        return cartas
    }
}