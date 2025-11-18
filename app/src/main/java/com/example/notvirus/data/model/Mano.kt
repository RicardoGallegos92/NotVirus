package com.example.notvirus.data.model

data class Mano(
    val cartas: List<Carta> = listOf(), // Cambiado a List inmutable
) {
    // Devuelve una nueva Mano con las cartas añadidas
    fun addCartas(nuevasCartas: List<Carta>): Mano {
        return this.copy(cartas = (this.cartas + nuevasCartas))
    }

    // Devuelve una nueva Mano sin las cartas seleccionadas
    fun removeSelectedCartas(): Mano {
        val nuevaMano = this.cartas.filter { !it.seleccionada }
        return this.copy(cartas = nuevaMano)
    }

    // Devuelve las cartas seleccionadas (sin modificar estado)
    fun takeSelectedCarta(): List<Carta> {
        val cartasSeleccionadas = cartas.filter { it.seleccionada }
        return cartasSeleccionadas
    }

    // Devuelve una nueva Mano con una carta marcada como seleccionada
    fun selectCarta(cartaElegida: Carta): Mano {
        val nuevasCartas = this.cartas.map { carta ->
            if (carta.id == cartaElegida.id) { // Comparar por ID único
                carta.copy(seleccionada = !carta.seleccionada)
            } else {
                carta
            }
        }
        return this.copy(cartas = nuevasCartas)
    }
}