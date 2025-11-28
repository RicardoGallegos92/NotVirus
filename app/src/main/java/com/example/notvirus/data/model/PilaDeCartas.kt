package com.example.notvirus.data.model

data class PilaDeCartas(
    val color: CartaColor,
    val pila: List<Carta> = emptyList(),
) {
    fun agregarCarta(carta: Carta): PilaDeCartas {
        val pilaActualizada = this.pila.toMutableList()
        pilaActualizada.add(carta)
        return this.copy(
            pila = pilaActualizada
        )
    }

    fun agregarCartas(cartas: List<Carta>): PilaDeCartas {
        val pilaActualizada = this.pila.toMutableList()
        pilaActualizada.addAll(cartas)
        return this.copy(
            pila = pilaActualizada
        )
    }

    fun tomarCartaTipo(tipo: CartaTipo): List<Carta> {
        return pila.filter{ it.tipo == tipo }
    }

    fun tomarCartasTodo(): List<Carta> {
        return getPila()
    }

    fun vaciarPila(tipo: CartaTipo): PilaDeCartas {
        return this.copy(
            pila = emptyList()
        )
    }

    fun getColor(): CartaColor {
        return this.color
    }

    fun getPila(): List<Carta> {
        return this.pila
    }
}