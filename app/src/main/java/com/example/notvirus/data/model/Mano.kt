package com.example.notvirus.data.model

data class Mano(
    val cartas: List<Carta> = listOf(), // Cambiado a List inmutable
) {
    // Devuelve una nueva Mano con las cartas añadidas
    fun agregarCartas(nuevasCartas: List<Carta>): Mano {
        return this.copy(
            cartas = ( this.cartas + nuevasCartas )
        )
    }

    /** @return Devuelve las Cartas 'Seleccionadas'
     *
     */
    fun tomarCartasSeleccionadas(): List<Carta> {
        return this.cartas.filter { it.estaSeleccionada }
    }

    /**
     * @return Devuelve la Mano sin las cartas seleccionadas
    */
    fun quitarCartasSeleccionadas(): Mano {
        println("Mano.quitarCartasSeleccionadas()")
        val nuevaMano = this.cartas.filter { !it.estaSeleccionada }
        return this.copy(
            cartas = nuevaMano
        )
    }

    // Devuelve una nueva Mano con una carta marcada como seleccionada
    fun seleccionarCarta(cartaElegida: Carta): Mano {
        val cartasActualizadas = this.cartas.map { carta ->
            if (carta.id == cartaElegida.id) { // Comparar por ID único
                carta.copy(
                    estaSeleccionada = !carta.estaSeleccionada
                )
            } else {
                carta
            }
        }

        return this.copy(
            cartas = cartasActualizadas
        )
    }
}