package com.example.notvirus.data.model

data class PilaDescarte(
    val pila: List<Carta> = listOf(), // Cambiado a val
) {
    fun addCarta(cartasDescartadas: List<Carta>): PilaDescarte { // Cambiado MutableList a List
        return this.copy(
            pila = this.pila + cartasDescartadas
        )
    }

    fun empty(): Pair<PilaDescarte, List<Carta>> { // Devuelve nueva pila vacía y las cartas anteriores
        return Pair(this.copy(pila = listOf()), this.pila)
    }
}