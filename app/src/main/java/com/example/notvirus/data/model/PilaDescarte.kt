package com.example.notvirus.data.model

class PilaDescarte(
    var pila: MutableList<Carta>,
) {
    fun addCarta(): Unit {

    }

    fun empty(): List<Carta> {
        var cartas: MutableList<Carta> = pila.map { it }.toMutableList()
        pila.clear()
        return cartas
    }
}