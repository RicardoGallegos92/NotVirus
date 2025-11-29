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
        println("Jugador.recibirCartasToMano()")
        val manoActualizada = mano.agregarCartas(nuevasCartas = nuevasCartas)
        return this.copy(
            mano = manoActualizada
        )
    }

    /** @return Jugador sin las cartas seleccionadas
    */
    fun quitarCartasSeleccionadas():Jugador{
        println("Jugador.quitarCartasSeleccionadas()")
        val manoActualizada = this.mano.quitarCartasSeleccionadas()
        return this.copy(
            mano = manoActualizada
        )
    }

    /** quita la carta seleccionada de la Mano del Jugador
     * @return Jugador con la mano sin la carta Seleccionada
     */
    fun quitarCartaJugada():Jugador{
        val manoActualizada = this.mano.quitarCartasSeleccionadas()
        return this.copy(
            mano = manoActualizada
        )
    }

    /**
     * @return Lista con las cartas 'seleccionadas'
     */
    fun entregarCartasSeleccionadas():List<Carta>{
        return this.mano.tomarCartasSeleccionadas()
    }

    /**
     * @return Jugador Marca/Desmarca una Carta
     */
    fun marcarCartaEnMano(carta: Carta): Jugador {
        println("Jugador.marcarCartaEnMano()")
        val manoAct = mano.seleccionarCarta(carta)
        return this.copy(
            mano = manoAct
        )
    }

    /**
     * @param carta carta que recibe el jugador para agregar a su Mesa
     * @param colorPilaObjetivo color de la Pila donde se ubicará la carta
     * @return jugador con la carta agregada a la Mesa
     */
    fun agregaCartaToMesa(carta:Carta, colorPilaObjetivo: CartaColor): Jugador{
        println("Jugador.agregaCartaToMesa()")
        return this.copy(
            mesa = this.mesa.agregarCarta(carta, colorPilaObjetivo)
        )
    }

    fun entregarCartaJugada(): Carta {
        println("Jugador.entregarCartaJugada()")
        val cartas = mano.tomarCartasSeleccionadas()
        val cartaJugada = cartas[0]

        return cartaJugada
    }

    /** actualiza el estado de todas las pilas de la Mesa
     * @return Jugador con su Mesa actualizada
     */
    fun actualizarEstadoMesa():Jugador{
        return this.copy(
            mesa = this.mesa.actualizarEstadoPilas()
        )
    }

    fun inmunizarPilaEnMesa(color: CartaColor): Jugador{
        return this.copy(
            mesa = this.mesa.inmunizarPila(color)
        )
    }

    fun esPilaInmune(color: CartaColor): Boolean{
        return ( this.mesa.getPilaDeColor(color).estado == PilaEstado.INMUNE )
    }

    fun existeOrgano(color: CartaColor): Boolean {
    // verifica si la pila ya contiene un órgano
        return ( this.mesa.getPilaDeColor(color).estado == PilaEstado.CON_ORGANO)
    }
}