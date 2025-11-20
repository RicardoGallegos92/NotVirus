package com.example.notvirus.data.model

data class PilaDescarte(
    val pila: List<Carta> = listOf(), // Cambiado a val
) {
    fun agregarCartas(cartasDescartadas: List<Carta>): PilaDescarte {
        return this.copy(
            pila = this.pila + cartasDescartadas
        )
    }

    fun tomarNCartas(n: Int): Pair<List<Carta>, PilaDescarte> {
        // Devuelve una lista de cartas y una 'PilaDescarte' actualizada
        return Pair(
            this.pila,
            this.copy(
                pila = listOf()
            )
        )
    }

    fun tomarTodasLasCartas(): Pair<List<Carta>, PilaDescarte> {
        // Devuelve una lista de cartas y una 'PilaDescarte' vacía
        return tomarNCartas(
            pila.size
        )
    }
}