package com.example.notvirus.data.model

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
        var barajaActual = baraja // Mantén un registro de la baraja actualizada
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
        try {
            var turno = this.copy()
            when {
                descartarCarta -> {
                    // Descartar 'n' cartas
                    turno = turno.descartarDesdeMano()
                    println("CARTAS DESCARTADAS: FINISHED")
                }

                jugarCarta -> {
                    // val jugadorObjetivo = seleccionarJugadorObjetivo()
                    // Jugar 1 carta
                    turno = turno.jugarCarta()

                    // PilaDeColor.estado -> ( inmunizarla || descartarla )
                    turno = turno.actualizarEstadosMesas()

                    // PilaDeColor -> ( inmunizarla || descartarla )
                    turno = turno.accionarEstadosMesas()

                    //
                    turno = turno.actualizarEstadosMesas()

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

            /// -> Cambio de Jugador
//            val cartaSeJugo = cartaFueJugada(turno.getJugadorByID(jugadorActivoID).mano)
//            val jugadorProximoTurno = if (cartaSeJugo){
//      false fuerza que no haya cambio de turno hasta que haya una IA
            val jugadorProximoTurno = if (false) {
                pasarTurno() // debe ocurrir -> solo si la jugada es exitosa
            } else {
                println("Se mantiene 'Jugador-Activo'")
                jugadorActivoID
            }

            return turno.copy(
                jugadorActivoID = jugadorProximoTurno,
            )
        } catch (e: Exception) {
            println("HUBO ERROR")
            println(e.message)
            return this.copy()
        }
    }

    fun cantCartasEnManoJugadorActivo(): Unit {
        println(
            "Jugador-Activo: llega con ${getJugadorByID(jugadorActivoID).getCantCartasEnMano()} cartas"
        )
    }

    fun actualizarEstadosMesas(): Juego {
        return this.copy(
            jugadores = this.jugadores.map { jugador: Jugador ->
                jugador.actualizarEstadoMesa()
            }
        )
    }

    /**
     * Revisa los estados de las pilas en la Mesa para tomar acciones
     * @return Juego con Mesas accionadas
     */
    fun accionarEstadosMesas(): Juego {
        var cartasParaDescartar: MutableList<Carta> = mutableListOf()

        val jugadoresAct = this.jugadores.map { jugador ->
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
//        println("Juego.jugarCarta()")
        // Juego toma la carta jugada
        val cartaJugada: Carta = this.tomarCartaJugada()
        // retiramos la carta de la mano del 'Jugado-Activo'
        var juegoActualizado = this.quitarCartaJugada()

        // distinguir entre CartaTipo
        when (cartaJugada.tipo) {
            CartaTipo.TRATAMIENTO -> {
                // activar Efecto del TRATAMIENTO

                // enviar 'Carta' a 'PilaDescarte'
                juegoActualizado = juegoActualizado.descartarCarta(cartaJugada)
            }

            CartaTipo.ORGANO -> {
                // Mesa propia
                val jugadorObjetivoDeTurno = juegoActualizado.getJugadorByID(jugadorActivoID)

                if ( jugadorObjetivoDeTurno.isPilaConEstado(cartaJugada.color, PilaEstado.VACIO) ) {
                    juegoActualizado = juegoActualizado.pasarCartaToMesaJugador(
                        cartaJugada = cartaJugada,
                        jugadorObjetivo = jugadorObjetivoDeTurno,
                        colorObjetivo = cartaJugada.color
                    )
                } else {
                    throw JugadaInvalida()
                }
            }

            CartaTipo.MEDICINA -> {
                val jugadorObjetivoDeTurno =
                    juegoActualizado.getJugadorByID(jugadorActivoID) // mesa propia

                if( jugadorObjetivoDeTurno.isPilaConEstado(cartaJugada.color, PilaEstado.CON_ORGANO) ) {
                    juegoActualizado = juegoActualizado.pasarCartaToMesaJugador(
                        cartaJugada = cartaJugada,
                        jugadorObjetivo = jugadorObjetivoDeTurno, // (provisorio) -> se debe agregar funcion para seleccionar objetivo
                        colorObjetivo = cartaJugada.color
                    )
                } else {
                    throw JugadaInvalida()
                }
            }

            CartaTipo.VIRUS -> {
                // jugador contrario
                // (provisorio) -> se debe agregar funcion para seleccionar objetivo
                val jugadorObjetivoDeTurno = juegoActualizado.getJugadorByID(pasarTurno())

                if( jugadorObjetivoDeTurno.isPilaConEstado(cartaJugada.color, PilaEstado.CON_ORGANO) ) {
                    juegoActualizado = juegoActualizado.pasarCartaToMesaJugador(
                        cartaJugada = cartaJugada,
                        jugadorObjetivo = getJugadorByID(pasarTurno()),
                        colorObjetivo = cartaJugada.color
                    )
                } else {
                    throw JugadaInvalida()
                }
            }

            else -> {
                println("Mensaje SnackBar -> Carta Jugada no posee un TIPO")
            }
        }

        return juegoActualizado
    }

    /**
     * @return id del jugador Objetivo
     */
    fun seleccionarJugadorObjetivo(): String {
        // insertar magia
        // return jugadorSeleccionado.id
        return getJugadorByID(pasarTurno()).id
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
        ///     this.cantCartasEnManoJugadorActivo()
        //  println("J-Objetivo: ${jugadorObjetivo.getCantCartasEnMano()}")

        // pasar la 'cartaJugada' a la mesa del 'jugadorObjetivo'
        val jugadorObjetivoActualizado = jugadorObjetivo.agregaCartaToMesa(
            carta = cartaJugada,
            // falta funcion para seleccionar la 'PilaObjetivo'
            colorPilaObjetivo = colorObjetivo
        )

//        println("J-ObjetivoActualizado: ${jugadorObjetivoActualizado.getCantCartasEnMano()}")

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
                pilaDescarte = pilaDescarteActualizada
            )
        } else {
            return this.copy()
        }
    }

    /** cambiar jugador en turno
     * @return id del nuevo jugador en turno
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

    fun cartaFueJugada(mano: Mano): Boolean {
        // awa
        val manoPrevia = this.getJugadorByID(jugadorActivoID).mano
        return mano == manoPrevia
    }

    fun marcarCarta(carta: Carta): Juego {
//        println("Juego.marcarCarta()")
        // cambia el estado "selecciona" de la carta entre "true" y "false"
        val jugadorActualizado = getJugadorByID(this.jugadorActivoID).marcarCartaEnMano(carta)

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
        // Lógica para determinar ganador
        // -> el jugador ganó si el conteo (turnosParaGanar) de su mesa es 0
        return this.copy(
            jugadorGanadorID = if (getJugadorByID(jugadorActivoID).getTurnosParaGanar() == 0) {
                jugadorActivoID
            } else {
                ""
            }
        )
    }
}