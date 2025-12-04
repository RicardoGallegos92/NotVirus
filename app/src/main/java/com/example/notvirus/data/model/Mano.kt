package com.example.notvirus.data.model

data class Mano(
    val cartas: List<Carta> = listOf(), // Cambiado a List inmutable
) {
    // Devuelve una nueva Mano con las cartas añadidas
    fun agregarCartas(nuevasCartas: List<Carta>): Mano {
        return this.copy(
            cartas = (this.cartas + nuevasCartas)
        )
    }

    /**
     * @return Devuelve las Cartas 'Seleccionadas'
     */
    fun tomarCartasSeleccionadas(): List<Carta> {
        return this.cartas.filter { it.estaSeleccionada }
    }

    /**
     * @return Devuelve la Mano sin las cartas seleccionadas
     */
    fun quitarCartasSeleccionadas(): Mano {
//        println("Mano.quitarCartasSeleccionadas()")
        val manoNueva = this.copy( cartas = this.cartas.filter { !it.estaSeleccionada } )
//        println("Mano se va con ${manoNueva.cartas.size} cartas")
        return manoNueva
    }

    /** @return Mano con una carta marcada como seleccionada
     *
     */
    fun seleccionarCarta(cartaID: String): Mano {
        return this.copy(
            cartas = this.cartas.map { carta: Carta ->
                if (carta.id == cartaID) { // Comparar por ID único
                    carta.copy(
                        estaSeleccionada = !carta.estaSeleccionada
                    )
                } else {
                    carta
                }
            }
        )
    }

    fun getCartaByID(cartaID: String): Carta{
        val carta =  this.cartas.filter { it.id == cartaID }
        if (carta.isEmpty()){
            throw CartaNoEnMano()
        }
        return carta[0]
    }
}