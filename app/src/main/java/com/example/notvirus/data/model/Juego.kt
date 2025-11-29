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

                    // PilaDeColor -> ( inmunizarla || descartarla )
                    turno = turno.revisarEstadosMesa()

                    turno = turno.hayGanador()
                    println("CARTA JUGADA: FINISHED")
                }
            }
            // verificamos la baraja antes de robar carta
            turno = turno.llenarBaraja(
                maxCartasEnMano - getJugadorByID(jugadorActivoID).mano.cartas.size
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

    fun revisarEstadosMesa(jugadorObjetivo: Jugador? = null):Juego{
        var cartasParaDescartar : List<Carta> = emptyList()
        val jugadoresAct = this.jugadores.map{jugador ->
            val pilasActuales = jugador.mesa.pilas.map{ pila: PilaDeColor ->
                when (pila.estado){
                    PilaEstado.DESCARTAR -> {
                        cartasParaDescartar = pila.tomarCartasTodo()
                        pila.quitarCartasTodo()
                    }
                    PilaEstado.DEJAR_SOLO_ORGANO -> {
                        cartasParaDescartar = pila.tomarCartasExcepto(CartaTipo.ORGANO)
                        pila.quitarCartasExcepto(CartaTipo.ORGANO)
                    }
                    PilaEstado.INMUNIZAR -> {
                        pila.inmunizar()
                    }
                    else -> { pila }
                    /*
                    /* hacen nada */
                    PilaEstado.INMUNE -> {}
                    PilaEstado.CON_ORGANO -> {}
                    PilaEstado.VACIO -> {}
                    */
                }
            }

            val mesaActual = jugador.mesa.copy(
                pilas = pilasActuales
            )

            jugador.copy(
                mesa = mesaActual
            )
        }
        var pilaDescarteAct = this.pilaDescarte.copy()
        if (cartasParaDescartar.isNotEmpty()){
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
        println("Juego.jugarCarta()")
        // Juego toma la carta jugada
        val cartaJugada: Carta = tomarCartaJugada()
        // retiramos la carta de la mano del 'Jugado-Activo'
        var juegoActualizado = quitarCartaJugada()


        // distinguir entre CartaTipo
        when (cartaJugada.tipo) {
            CartaTipo.TRATAMIENTO -> {
                // activar Efecto del TRATAMIENTO

                // quitar 'Carta' de la 'Mano' y enviar 'Carta' a 'PilaDescarte'
                juegoActualizado = descartarDesdeMano()
            }

            CartaTipo.ORGANO -> {
                val jugadorObjetivoDeTurno = getJugadorByID(jugadorActivoID) /// mesa propia

                if (!jugadorObjetivoDeTurno.existeOrgano(color = cartaJugada.color)
                    && !jugadorObjetivoDeTurno.esPilaInmune(color = cartaJugada.color)
                ) {
                    juegoActualizado = juegoActualizado.pasarCartaToMesa(
                        cartaJugada = cartaJugada,
                        jugadorObjetivo = jugadorObjetivoDeTurno,
                        colorObjetivo = cartaJugada.color
                    )
                } else {
                    throw JugadaInvalida()
                }
            }

            CartaTipo.MEDICINA -> {
                val jugadorObjetivoDeTurno = getJugadorByID(jugadorActivoID) // mesa propia

                if (jugadorObjetivoDeTurno.existeOrgano(color = cartaJugada.color)
                    && !jugadorObjetivoDeTurno.esPilaInmune(color = cartaJugada.color)
                ) {
                    juegoActualizado = pasarCartaToMesa(
                        cartaJugada = cartaJugada,
                        jugadorObjetivo = jugadorObjetivoDeTurno, // (provisorio) -> se debe agregar funcion para seleccionar objetivo
                        colorObjetivo = cartaJugada.color
                    )
                } else {
                    throw JugadaInvalida()
                }
            }

            CartaTipo.VIRUS -> {
                // (provisorio) -> se debe agregar funcion para seleccionar objetivo
                val jugadorObjetivoDeTurno = getJugadorByID(pasarTurno()) // jugador contrario

                if (jugadorObjetivoDeTurno.existeOrgano(color = cartaJugada.color)
                    && !jugadorObjetivoDeTurno.esPilaInmune(color = cartaJugada.color)
                ) {
                    juegoActualizado = pasarCartaToMesa(
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


        // actualizamos el estado de toda la Mesa tras insertar carta
        return juegoActualizado.copy(
            jugadores = juegoActualizado.jugadores.map { jugador ->
                jugador.actualizarEstadoMesa()
            }
        )
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
    fun pasarCartaToMesa(
        cartaJugada: Carta,
        jugadorObjetivo: Jugador,
        colorObjetivo: CartaColor,
    ): Juego {
        println("Juego.pasarCartaToMesa()")
        // pasar la 'cartaJugada' a la mesa del 'jugadorObjetivo'
        val jugadorObjetivoActualizado = jugadorObjetivo.agregaCartaToMesa(
            carta = cartaJugada,
            // falta funcion para seleccionar la 'PilaObjetivo'
            colorPilaObjetivo = colorObjetivo
        )

        val jugadoresActualizados = this.jugadores.map { jugador ->
            when (jugador.id) {
                jugadorObjetivo.id -> jugadorObjetivoActualizado // Mesa Actualizada
                else -> jugador
            }
        }

        return this.copy(
            jugadores = jugadoresActualizados,
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

    /**
     * @return Jugador-Activo con mano llena
     */
    fun llenarManoJugadorActivo(): Juego {
        println("Juego.llenarManoJugadorActivo()")
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
        println("Juego.tomarCartaJugada()")
        return getJugadorByID(jugadorActivoID).entregarCartaJugada()
    }

    /** quita de la mano del 'Jugador-Activo' la carta seleccionada para jugar
     * @return Juego con el 'Jugador-Activo' sin la carta jugada
     */
    fun quitarCartaJugada(): Juego {
        val jugadorActualizado = getJugadorByID(jugadorActivoID).quitarCartaJugada()
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
     * @return Baraja con suficientes cartas para robar
     */
    fun llenarBaraja(cartasNecesarias: Int = maxCartasEnMano): Juego {
        println("Juego.llenarBaraja()")
        if (baraja.pila.size < cartasNecesarias) {
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
            //println("Juego.llenarBaraja() : else -> return")
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
    fun hayGanador(): Juego {
        println("Juego.hayGanador()")
        // Lógica para determinar ganador
        // -> el jugador ganó si el conteo (turnosParaGanar) de su mesa es 0
        val jugadorGanador = jugadores.filter { jugador: Jugador ->
            jugador.mesa.turnosParaGanar == 0
        }

        return this.copy(
            jugadorGanadorID = if (jugadorGanador.isNotEmpty()) {
                jugadorGanador[0].id
            } else {
                ""
            }
        )
    }
}