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

    fun tomarCartasSeleccionadas():Pair<List<Carta>,Mano> {
    // Devuelve las 'Cartas-Seleccionadas' y la 'Mano' actualizada
        return Pair(
            cartas.filter { it.estaSeleccionada }
            ,
            this.copy(
                cartas = cartas.filter { !it.estaSeleccionada }
            )
        )
    }

    // Devuelve una nueva Mano sin las cartas seleccionadas
    fun removeSelectedCartas(): Mano {
        val nuevaMano = this.cartas.filter { !it.estaSeleccionada }
        return this.copy(cartas = nuevaMano)
    }

    // Devuelve una nueva Mano con una carta marcada como seleccionada
    fun selectCarta(cartaElegida: Carta): Mano {
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