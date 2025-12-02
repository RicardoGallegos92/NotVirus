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
    fun quitarCartasSeleccionadas():Jugador{
//        println("Jugador.quitarCartasSeleccionadas()")
        return this.copy(
            mano = this.mano.quitarCartasSeleccionadas()
        )
    }

    /** quita la carta seleccionada de la Mano del Jugador
     * @return Jugador con la mano sin la carta Seleccionada
     */
    fun quitarCartaJugada():Jugador{
        return this.copy(
            mano = this.mano.quitarCartasSeleccionadas()
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
//        println("Jugador.marcarCartaEnMano()")
        return this.copy(
            mano = this.mano.seleccionarCarta(carta)
        )
    }

    /**
     * @param carta carta que recibe el jugador para agregar a su Mesa
     * @param colorPilaObjetivo color de la Pila donde se ubicará la carta
     * @return jugador con la carta agregada a la Mesa
     */
    fun agregaCartaToMesa(carta:Carta, colorPilaObjetivo: CartaColor): Jugador{
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
/*
    /** actualiza el estado de todas las pilas de la Mesa
     * @return Jugador con su Mesa actualizada
     */
    fun actualizarEstadoMesa():Jugador{
        return this.copy(
            mesa = this.mesa.actualizarEstados()
        )
    }
*/
/*
    fun inmunizarPila(color: CartaColor): Jugador{
        return this.copy(
            mesa = this.mesa.inmunizarPila(color)
        )
    }
*/
    /**
     * @param color color de la PilaDeColor que se revisa
     * @return [true] -> la pila de color indicado es INMUNE
     * @return [false] -> la pila de color indicado NO es INMUNE
     */
    fun esPilaInmune(colorPila: CartaColor): Boolean{
        return ( this.getEstadoPila(colorPila) == PilaEstado.INMUNE )
    }

    /**
     * @return el estado de la Pila con el [color] indicado
     */
    fun getEstadoPila(colorPila: CartaColor): PilaEstado {
        return this.mesa.getEstadoPila(colorPila)
    }

    /**
     * @param color color de la PilaDeColor que se revisa
     * @return [true] -> la pila de color indicado tiene un ORGANO
     * @return [false] -> la pila de color indicado NO tiene un ORGANO
     */
    fun existeOrgano(colorPila: CartaColor): Boolean {
    // verifica si la pila ya contiene un órgano
        return ( this.mesa.getPilaDeColor(colorPila).estado == PilaEstado.CON_ORGANO)
    }

    fun getPilaDeColor(color: CartaColor): PilaDeColor{
        return this.mesa.getPilaDeColor(color)
    }

    fun getTurnosParaGanar(): Int{
        return this.mesa.turnosParaGanar
    }

    /**
     * @return Lista de cartas para Descartar
     */
    fun tomarCartasSegunEstadoMesa():List<Carta>{
        return this.mesa.tomarCartasDePilasSegunEstado()
    }

    /**
     * @return Jugador con las cartas señaladas removidas de su Mesa
     */
    fun accionarEstadosMesa():Jugador{
        return this.copy(
            mesa = this.mesa.accionarEstados()
        )
    }

    fun getCantCartasEnMano():Int{
        return this.mano.cartas.size
    }

    /**
     * @return [true] si la pila de color indicado tiene el estado solicitado
     */
    fun isPilaConEstado(
        colorPila: CartaColor,
        estado: PilaEstado,
    ): Boolean {
        return (this.getEstadoPila(colorPila) == estado)
    }
}