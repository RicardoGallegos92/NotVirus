package com.example.notvirus.data.model

import android.util.Log

data class Juego(
    val jugadores: List<Jugador>,
    val baraja: Baraja,
    val pilaDescarte: PilaDescarte,

    val jugadorActivoID: String,
    val jugadorGanadorID: String,

    val maxCartasEnMano: Int,
) {
    constructor() : this(
        jugadores = listOf(Jugador(nombre = "CPU"), Jugador(nombre = "Jugador1")),
        baraja = Baraja(),
        pilaDescarte = PilaDescarte(),

        jugadorActivoID = "",
        jugadorGanadorID = "",

        maxCartasEnMano = 3,
    )

    /**
     * @return Devuelve un nuevo Juego con manos repartidas y primer jugador activo
     */
    fun empezarJuego(): Juego {
//        println("Juego.empezarJuego()")
        var barajaActual = this.baraja.copy() // Mantén un registro de la baraja actualizada
        val jugadoresConCartas = jugadores.map { jugador ->
            val (cartasParaJugador, nuevaBaraja) = barajaActual.pedirCartas(maxCartasEnMano) // <-- solicitar cartas para la mano
            barajaActual = nuevaBaraja // Actualiza la baraja para el siguiente jugador
            jugador.recibirCartasToMano(cartasParaJugador) // <-- Pasa la lista de cartas
        }

        val nuevoJugadorActivoID = pasarTurno()

        return this.copy(
            jugadores = jugadoresConCartas,
            baraja = barajaActual, // <-- Baraja-Actualizada
            jugadorActivoID = nuevoJugadorActivoID,
        )
    }

    /** Ciclo lógico del turno
     * @return Juego con el turno finalizado
     */
    fun usarTurno(jugarCarta: Boolean = false, descartarCarta: Boolean = false): Juego {
        val TAG = "Juego -> usarTurno()"
        try {
            var turno = this.copy()
            when {
                descartarCarta -> {
                    // Descartar 'n' cartas
                    turno = turno.descartarDesdeMano()
                    //println("CARTAS DESCARTADAS: FINISHED")
                }

                jugarCarta -> {
                    // val jugadorObjetivo = seleccionarJugadorObjetivo()
                    // Jugar 1 carta
                    turno = turno.jugarCarta()

                    // PilaDeColor.estado -> ( Inmune || Vacio || Con_Organo )
                    turno = turno.accionarEstadosMesas()

                    // verificar si ya ganó
                    turno = turno.setGanador()
                }
            }

            // verificamos la baraja antes de robar carta
            turno = turno.llenarBaraja(
                maxCartasEnMano - turno.getJugadorByID(jugadorActivoID).getCantCartasEnMano()
            )

            // Robar para completar la mano
            turno = turno.llenarManoJugadorActivo()

            // Fin del Turno
            return turno.copy(
                jugadorActivoID = turno.pasarTurno() // jugadorProximoTurno,
            )
        } catch (e: Exception) {
            Log.i(TAG, "HUBO ERROR")
            Log.d(TAG, e.message.toString())
            return this.copy()
        }
    }

    fun cantCartasEnManoJugadorActivo() {
        val TAG = "Juego->cantCartasEnManoJugadorActivo()"
        Log.i(
            TAG,
            "Jugador-Activo: con ${getJugadorByID(jugadorActivoID).getCantCartasEnMano()} cartas"
        )
    }
    /*
        fun actualizarEstadosMesas(): Juego {
            return this.copy(
                jugadores = this.jugadores.map { jugador: Jugador ->
                    jugador.actualizarEstadoMesa()
                }
            )
        }
    */

    /**
     * Revisa los estados de las pilas en la Mesa para tomar acciones
     * @return Juego con Mesas accionadas
     */
    fun accionarEstadosMesas(): Juego {
        println("Juego->accionarEstadosMesas()")
        val cartasParaDescartar: MutableList<Carta> = mutableListOf()

        val jugadoresAct = this.jugadores.map { jugador ->
            // deben ser todos los jugadores
            // ya que pueden jugarse cartas en cualquier Mesa
            cartasParaDescartar.addAll(jugador.tomarCartasSegunEstadoMesa())
            jugador.accionarEstadosMesa()
        }

        var pilaDescarteAct = this.pilaDescarte.copy()
        // descartamos si se dió el caso
        if (cartasParaDescartar.isNotEmpty()) {
            pilaDescarteAct = this.pilaDescarte.agregarCartas(cartasParaDescartar)
        }

        return this.copy(
            jugadores = jugadoresAct,
            pilaDescarte = pilaDescarteAct,
        )
    }

    /** toma carta que jugará el 'Jugador-Activo'
     * -> pasar carta al jugadorObjetivo -> jugador lo manda a su mesa
     * -> actualizar estado de la pila en la mesa
     *  @return Juego con la carta aplicada
     */
    fun jugarCarta(jugadorObjetivo: Jugador? = null): Juego {
        val TAG = "Juego->jugarCarta()"
        // Juego toma la carta jugada
        val cartaJugada: Carta = this.tomarCartaJugada()
        // retiramos la carta de la mano del 'Jugado-Activo'
        var juegoActualizado = this.quitarCartaJugada()

        // distinguir entre CartaTipo
        when (cartaJugada.tipo) {
            CartaTipo.TRATAMIENTO -> {
                // activar Efecto del TRATAMIENTO
                juegoActualizado = juegoActualizado.usarTratamiento(cartaJugada);
                // enviar 'Carta' a 'PilaDescarte'
                juegoActualizado = juegoActualizado.descartarCarta(cartaJugada)
            }

            CartaTipo.ORGANO -> {
                // Mesa propia
                val jugadorObjetivo = juegoActualizado.getJugadorByID(jugadorActivoID)

                if (jugadorObjetivo.isPilaConEstado(cartaJugada.color, PilaEstado.VACIO)) {
                    juegoActualizado = juegoActualizado.pasarCartaToMesaJugador(
                        cartaJugada = cartaJugada,
                        jugadorObjetivo = jugadorObjetivo,
                        colorObjetivo = cartaJugada.color
                    )
                } else {
                    throw PilaConOrgano()
                }
            }

            CartaTipo.MEDICINA -> {
                val jugadorObjetivo =
                    juegoActualizado.getJugadorByID(jugadorActivoID) // mesa propia

                if (jugadorObjetivo.isPilaConEstado(cartaJugada.color, PilaEstado.CON_ORGANO)) {
                    juegoActualizado = juegoActualizado.pasarCartaToMesaJugador(
                        cartaJugada = cartaJugada,
                        jugadorObjetivo = jugadorObjetivo, // (provisorio) -> se debe agregar funcion para seleccionar objetivo
                        colorObjetivo = cartaJugada.color
                    )
                } else {
                    throw PilaSinOrgano()
                }
            }

            CartaTipo.VIRUS -> {
                // jugador contrario
                // (provisorio) -> se debe agregar funcion para seleccionar objetivo
                val jugadorObjetivo = juegoActualizado.getJugadorByID(seleccionarJugadorObjetivoId())

                if (jugadorObjetivo.isPilaConEstado(cartaJugada.color, PilaEstado.CON_ORGANO)) {
                    juegoActualizado = juegoActualizado.pasarCartaToMesaJugador(
                        cartaJugada = cartaJugada,
                        jugadorObjetivo = jugadorObjetivo,
                        colorObjetivo = cartaJugada.color
                    )
                } else {
                    throw PilaSinOrgano()
                }
            }

            else -> {
                throw CartaSinTipo()
            }
        }

        return juegoActualizado
    }

    /**
     * @return id del jugador Objetivo
     */
    fun seleccionarJugadorObjetivoId(): String {
        // insertar magia
        // return jugadorSeleccionado.id
        return pasarTurno()
    }

    /**
     * @param cartaJugada Carta que se pasará a la mesa del jugador
     * @param jugadorObjetivo Jugador que recibe la Carta
     * @param colorObjetivo color de la Pila donde se debe agregar la cartaJugada
     * @return Juego con cartaJugada agregada a Mesa del jugadorObjetivo
     */
    fun pasarCartaToMesaJugador(
        cartaJugada: Carta,
        jugadorObjetivo: Jugador,
        colorObjetivo: CartaColor,
    ): Juego {
//        println("Juego.pasarCartaToMesaJugador()")
        // pasar la 'cartaJugada' a la mesa del 'jugadorObjetivo'
        val jugadorObjetivoActualizado = jugadorObjetivo.agregaCartaToMesa(
            carta = cartaJugada,
            // falta funcion para seleccionar la 'PilaObjetivo'
            colorPilaObjetivo = colorObjetivo
        )

        return this.copy(
            jugadores = this.jugadores.map { jugador ->
                when (jugador.id) {
                    jugadorObjetivo.id -> jugadorObjetivoActualizado // Mesa Actualizada
                    else -> jugador
                }
            }
        )
    }

    /**
     * @return jugadorActivo con Mano incompleta y las cartas descartadas ya en la 'Pila de Descarte'
     */
    fun descartarDesdeMano(): Juego {
//        println("Juego.descartarDesdeMano()")
        // tomamos las cartas seleccionadas del Jugador-Activo
        val cartasDescartadas = getJugadorByID(jugadorActivoID).entregarCartasSeleccionadas()
        // quitamos las cartas seleccionadas de la mano del Jugador-Activo
        val jugadorActivoActualizado = getJugadorByID(jugadorActivoID).quitarCartasSeleccionadas()
        return this.copy(
            // actualizamos la lista de jugadores
            jugadores = this.jugadores.map { jugador ->
                if (jugador.id == jugadorActivoActualizado.id) {
                    jugadorActivoActualizado
                } else {
                    jugador
                }
            },
            // enviamos las cartas seleccionadas para descartar a la 'Pila de Descarte'
            pilaDescarte = pilaDescarte.agregarCartas(cartasDescartadas),
        )
    }

    fun descartarCarta(cartasParaDescartar: List<Carta>): Juego {
        return this.copy(
            pilaDescarte = pilaDescarte.agregarCartas(cartasParaDescartar)
        )
    }

    fun descartarCarta(cartaParaDescartar: Carta): Juego {
        return this.copy(
            pilaDescarte = pilaDescarte.agregarCartas(listOf(cartaParaDescartar))
        )
    }

    /**
     * @return Jugador-Activo con mano llena
     */
    fun llenarManoJugadorActivo(): Juego {
//        println("Juego.llenarManoJugadorActivo()")
        val jugadorManoIncompleta = getJugadorByID(this.jugadorActivoID)
        val (nuevasCartasToMano, barajaActualizada) = baraja.pedirCartas((maxCartasEnMano - jugadorManoIncompleta.mano.cartas.size))

        val jugadorManoLlena = jugadorManoIncompleta.recibirCartasToMano(nuevasCartasToMano)

        return this.copy(
            jugadores = this.jugadores.map { jugador ->
                if (jugador.id == jugadorManoLlena.id) {
                    jugadorManoLlena
                } else {
                    jugador
                }
            },
            baraja = barajaActualizada,
        )
    }

    /** toma la carta seleccionada del 'Jugador-Activo'
     * @return Carta que el 'Jugador-Activo' tiene seleccionada en la Mano
     */
    fun tomarCartaJugada(): Carta {
//        println("Juego.tomarCartaJugada()")
        return getJugadorByID(jugadorActivoID).entregarCartaJugada()
    }

    /** quita de la mano del 'Jugador-Activo' la carta seleccionada para jugar
     * @return Juego con el 'Jugador-Activo' sin la carta jugada en Mano
     */
    fun quitarCartaJugada(): Juego {
//        println("Juego.quitarCartaJugada()")
        return this.copy(
            jugadores = this.jugadores.map { jugador ->
                if (jugador.id == jugadorActivoID) {
                    getJugadorByID(jugadorActivoID).quitarCartaJugada()
                } else {
                    jugador
                }
            }
        )
    }

    /**
     * @return Baraja con suficientes cartas para robar
     */
    fun llenarBaraja(cartasNecesarias: Int = maxCartasEnMano): Juego {
//        println("Juego.llenarBaraja()")
        if (baraja.pila.size < cartasNecesarias) {
            println("Descarte -> Baraja")
            val (cartasPila, pilaDescarteActualizada) = pilaDescarte.tomarTodasLasCartas()
            val cartasParaAgregar = cartasPila.map { carta: Carta ->
                carta.copy(
                    estaSeleccionada = false,
                    esInmune = false,
                )
            }
            return this.copy(
                baraja = baraja.agregarCartas(cartasParaAgregar).revolver(),
                pilaDescarte = pilaDescarteActualizada,
            )
        } else {
            return this
        }
    }

    /**
     * @return id del siguiente jugador de turno
     */
    fun pasarTurno(): String {
//        println("Juego.pasarTurno()")
        if (jugadorActivoID.isEmpty()) {
            // jugador inincial por defecto
            return jugadores[1].id // -> el player humano
        } else {
            val index = jugadores.indexOf(getJugadorByID(jugadorActivoID))
            return jugadores[(index + 1) % jugadores.size].id
        }
    }

    fun getJugadorByID(id: String): Jugador {
//        println("Juego.getJugadorByID()")
        return this.jugadores.filter { jugador ->
            jugador.id == id
        }[0]
    }

    /**
     * @return Jugador Marca/Desmarca una Carta
     */
    fun marcarCarta(cartaID: String): Juego {
//        println("Juego.marcarCarta()")
        // cambia el estado "selecciona" de la carta entre "true" y "false"
        val jugadorActualizado = getJugadorByID(this.jugadorActivoID).marcarCartaEnMano(cartaID)

        return this.copy(
            jugadores = this.jugadores.map { jugador ->
                if (jugador.id == jugadorActualizado.id) {
                    jugadorActualizado
                } else {
                    jugador
                }
            }
        )
    }

    /**
     * Verifica si el jugadorActivo ganó en su turno
     */
    fun setGanador(): Juego {
///        println("Juego.setGanador()")
        // -> el jugador ganó si el conteo (turnosParaGanar) de su mesa es 0
        return this.copy(
            jugadorGanadorID = if (getJugadorByID(jugadorActivoID).getTurnosParaGanar() <= 0) {
                jugadorActivoID
            } else {
                ""
            }
        )
    }

    fun getBarajaSize(): Int {
        return this.baraja.getSize()
    }

    fun getDescarteSize(): Int {
        return this.pilaDescarte.getSize()
    }

    /**
     * Determina el efecto que se aplicará según el tratamiento usado
     */
    fun usarTratamiento(cartaJugada: Carta): Juego {
        return when (cartaJugada.imagen) {
            CartaImagen.TRATAMIENTO_CONTAGIO -> { usarContagio() }
            CartaImagen.TRATAMIENTO_ERROR_MEDICO -> { usarErrorMedico() }
            CartaImagen.TRATAMIENTO_GUANTE_LATEX -> { usarGuanteLatex() }
            CartaImagen.TRATAMIENTO_ROBO_ORGANO -> { usarRoboOrgano() }
            CartaImagen.TRATAMIENTO_TRASPLANTE -> { usarTrasplante() }
            else -> { throw CartaSinTipo() }
        }
    }

    fun usarContagio():Juego{
        // Traslada todos los virus que puedas de tus órganos a los de otros jugadores.
        // Sólo se podrán contagiar órganos libres:
        // -> NO infectados
        // -> NO vacunados.
        val jugOpID = seleccionarJugadorObjetivoId()

        val mesaJugAct = this.getJugadorByID(jugadorActivoID).mesa
        val mesaJugObj = this.getJugadorByID(jugOpID).mesa

        val virusParaContagiar = mutableListOf<Carta>()
        mesaJugAct.pilas.map{ pila: PilaDeColor ->
            if( pila.cartas.size == 2 ){
                if ( pila.cartas[1].tipo == CartaTipo.VIRUS){
                    virusParaContagiar.add(pila.cartas[1])
                }
            }
        }
            TODO()

        return this.copy(

        )
    }

    /** intercambia Mesas de los Jugadores.
     * @return Juego con las mesas intercambiadas
     */
    fun usarErrorMedico():Juego{
        val jugOpID = seleccionarJugadorObjetivoId()
        val auxJuActMesa = this.getJugadorByID(jugadorActivoID).mesa
        val auxJuOpMesa = this.getJugadorByID(jugOpID).mesa

        val auxJuAct = this.getJugadorByID(jugadorActivoID).copy( mesa = auxJuOpMesa )
        val auxJuOp = this.getJugadorByID(jugOpID).copy( mesa = auxJuActMesa )

        return this.copy(
            jugadores = this.jugadores.map{ jugador ->
                when(jugador.id){
                    jugadorActivoID -> auxJuAct
                    jugOpID -> auxJuOp
                    else -> jugador
                }
            }
        )
    }

    fun usarGuanteLatex():Juego{
        // todos los oponentes se descartan de todas las cartas de su mano.
        TODO()
        return this
    }

    fun usarRoboOrgano():Juego{
        // roba un órgano cualquiera al cuerpo de otro jugador.
        TODO()
        return this
    }

    fun usarTrasplante():Juego{
        // Intercambia un órgano por otro entre dos jugadores cualesquiera.
        // Está prohibido trasplantar órganos inmunizados.
        TODO()
        return this
    }
}

