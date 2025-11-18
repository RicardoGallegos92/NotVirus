package com.example.notvirus.data.model

data class Jugador(
    val nombre: String = "Player X", // nombre de Usuario
    val isActive: Boolean = false,
    val mano: Mano = Mano(),
    val mesa: Mesa = Mesa(),
) {
    // Método para "tomar cartas" y devolver un nuevo Jugador con la mano actualizada
    fun takeCartas(nuevasCartas:List<Carta>): Jugador {
        println("Jugador.takeCartas()")
        val nuevaMano = mano.addCartas(nuevasCartas = nuevasCartas)
        return this.copy(mano = nuevaMano)
    }

    // Método para "descartar cartas" y devolver un nuevo Jugador con la mano actualizada
    fun discardCartas(): Pair<Jugador, List<Carta>> {
        println("Jugador.discardCartas()")
        // jugador separa las cartas a descartar
        val cartasDescartadas = mano.takeSelectedCarta()
        // jugador mantiene en mano las NO seleccionadas
        val nuevaMano = mano.removeSelectedCartas()
        val nuevoJugador = this.copy(mano = nuevaMano)
        // jugador pasa las cartas para descartar
        return Pair(nuevoJugador, cartasDescartadas)
    }

    fun actualizarMano(index: Int): Jugador{
        val manoAct = mano.selectCarta(mano.cartas[index])
        return this.copy(
            mano = manoAct
        )
    }

    // Método para "jugar una carta" y devolver un nuevo Jugador con la mano actualizada
    fun playCarta(): Pair<Jugador, Carta> {
        val cartaSeleccionada = mano.takeSelectedCarta()
        if (cartaSeleccionada.size != 1) {
            // tomar cartas de la pila de descarte
        }
        val cartaJugada = cartaSeleccionada[0]
        val nuevaMano = mano.removeSelectedCartas()
        val nuevoJugador = this.copy(mano = nuevaMano)
        return Pair(nuevoJugador, cartaJugada)
    }
}