package com.example.notvirus.data.model

data class PilaDescarte(
    val pila: List<Carta> = emptyList(),
) {
    /** @return Pila de Descarte con las cartas agregadas
    */
    fun agregarCartas(cartasDescartadas: List<Carta>): PilaDescarte {
        return this.copy(
            pila = this.pila + cartasDescartadas
        )
    }

    /**
     * @return <lista de cartas, Pila de Descarte Actualizada>
     */
    fun tomarNCartas(n: Int): Pair<List<Carta>, PilaDescarte> {
        return Pair(
            this.pila,
            this.copy(
                pila = emptyList()
            )
        )
    }

    /**
     * @return <lista de cartas, Pila de Descarte vacía>
     */
    fun tomarTodasLasCartas(): Pair<List<Carta>, PilaDescarte> {
        // Devuelve una lista de cartas y una 'PilaDescarte' vacía
        return tomarNCartas(
            pila.size
        )
    }
}