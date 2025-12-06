package com.example.notvirus.data.model

import java.util.UUID

data class Jugador(
    val nombre: String = "Player X", // nombre de Usuario
    val isActive: Boolean = false,
    val mano: Mano = Mano(),
    val mesa: Mesa = Mesa(),
    val id:String = UUID.randomUUID().toString()
) {
    fun recibirCartasToMano(nuevasCartas: List<Carta>): Jugador {
        // Metodo para "recibir cartas"
        /// -> "agregar a la mano" y devolver el Jugador con la mano actualizada
//        println("Jugador.recibirCartasToMano()")
        val manoActualizada = mano.agregarCartas(nuevasCartas = nuevasCartas)
        return this.copy(
            mano = manoActualizada
        )
    }

    /** @return Jugador sin las cartas seleccionadas
     */
    fun quitarCartasSeleccionadas(): Jugador {
//        println("Jugador.quitarCartasSeleccionadas()")
        return this.copy(
            mano = this.mano.quitarCartasSeleccionadas()
        )
    }

    /** quita la carta seleccionada de la Mano del Jugador
     * @return Jugador con la mano sin la carta Seleccionada
     */
    fun quitarCartaJugada(): Jugador {
        return this.copy(
            mano = this.mano.quitarCartasSeleccionadas()
        )
    }

    /**
     * @return Lista con las cartas 'seleccionadas'
     */
    fun entregarCartasSeleccionadas(): List<Carta> {
        return this.mano.tomarCartasSeleccionadas()
    }

    /**
     * @return Jugador Marca/Desmarca una Carta
     */
    fun marcarCartaEnMano(cartaID: String): Jugador {
//        println("Jugador.marcarCartaEnMano()")
        return this.copy(
            mano = this.mano.seleccionarCarta(cartaID)
        )
    }

    /**
     * @param carta carta que recibe el jugador para agregar a su Mesa
     * @param colorPilaObjetivo color de la Pila donde se ubicará la carta
     * @return jugador con la carta agregada a la Mesa
     */
    fun agregaCartaToMesa(carta: Carta, colorPilaObjetivo: CartaColor): Jugador {
//        println("Jugador.agregaCartaToMesa()")
        return this.copy(
            mesa = this.mesa.agregarCarta(carta, colorPilaObjetivo)
        )
    }

    /**
     * @return [Carta] seleccionada que el 'Jugador-Activo' tiene en la mano
     */
    fun entregarCartaJugada(): Carta {
//        println("Jugador.entregarCartaJugada()")
        val cartas = mano.tomarCartasSeleccionadas()
        val cartaJugada = cartas[0]

        return cartaJugada
    }

    /**
     * @return el estado de la Pila con el [color] indicado
     */
    fun getEstadoPila(colorPila: CartaColor): PilaEstado {
        return this.mesa.getEstadoPila(colorPila)
    }

    fun getTurnosParaGanar(): Int {
        return this.mesa.turnosParaGanar
    }

    /**
     * @return Lista de cartas para Descartar
     */
    fun tomarCartasSegunEstadoMesa(): List<Carta> {
        return this.mesa.tomarCartasDePilasSegunEstado()
    }

    /**
     * @return Jugador con las cartas señaladas removidas de su Mesa
     */
    fun accionarEstadosMesa(): Jugador {
        return this.copy(
            mesa = this.mesa.accionarEstados()
        )
    }

    fun getCantCartasEnMano(): Int {
        return this.mano.cartas.size
    }

    /**
     * @return [true] si la pila de color indicado tiene el estado solicitado
     */
    fun isPilaConEstado(colorPila: CartaColor, estado: PilaEstado): Boolean {
        return (this.getEstadoPila(colorPila) == estado)
    }

    fun getCartaManoByID(id: String): Carta {
        return this.mano.getCartaByID(id)
    }

    fun getCartaManoByIndex(index: Int):Carta{
        return this.mano.getCartaByIndex(index)
    }
}