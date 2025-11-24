package com.example.notvirus.data.model

data class Juego(
    val jugadores: List<Jugador>,
    val baraja: Baraja,
    val pilaDescarte: PilaDescarte,

    val jugadorActivo: Jugador?,
    val jugadorGanador: Jugador?,

    val maxCartasEnMano: Int,
) {
    constructor() : this(
        jugadores = listOf(Jugador(nombre = "CPU"), Jugador(nombre = "Jugador1")),
        baraja = Baraja(),
        pilaDescarte = PilaDescarte(),

        jugadorActivo = null,
        jugadorGanador = null,

        maxCartasEnMano = 3,
    )

    // empezarJuego: Devuelve un nuevo Juego con manos repartidas y primer jugador activo
    fun empezarJuego(): Juego {
        var barajaActual = baraja // Mantén un registro de la baraja actualizada
        val nuevosJugadores = jugadores.map { jugador ->
            val (cartasParaJugador, nuevaBaraja) = barajaActual.pedirCartas(maxCartasEnMano) // <-- solicitar cartas para la mano
            barajaActual = nuevaBaraja // Actualiza la baraja para el siguiente jugador
            jugador.recibirCartas(cartasParaJugador) // <-- Pasa la lista de cartas
        }
        /*
                // Activar primer jugador
                val jugadorInicial = nuevosJugadores[0]
                val jugadorConEstadoActivo = jugadorInicial.copy(isActive = true)
                nuevosJugadores[0] = jugadorConEstadoActivo
        */
        return this.copy(
            jugadores = nuevosJugadores,
            baraja = barajaActual, // <-- Asegúrate de usar la baraja actualizada
            jugadorActivo = pasarTurno(),
        )
    }

    fun hayGanador(): Pair<Boolean, Jugador?> {
        // Lógica para determinar ganador
        // -> el jugador ganó si el conteo (turnosParaGanar) de su mesa es 0
        val jugadorGanador = jugadores.filter { jugador: Jugador ->
            jugador.mesa.turnosParaGanar == 0
        }
        return if (jugadorGanador.isEmpty()) {
            Pair(true, jugadorGanador[0])
        } else {
            Pair(false, null)
        }
    }

    fun jugarCarta(): Juego {
        val carta: Carta = tomarCartaJugada()

        return this.copy(

        )
    }

    // passCartasToPilaDescarte: Devuelve un nuevo Juego con cartas descartadas
    fun passCartasToPilaDescarte(): Juego {
        // no hace falta lógica extra, las cartas pasan de la mano a la pila
        println("Juego.passCartasToPilaDescarte()")
        val (nuevoJugador, cartasDescartadas) = jugadores[1].discardCartas()
        // pila descarte actualizada
        val nuevaPilaDescarte = pilaDescarte.agregarCartas(cartasDescartadas)
        // baraja actualizada y cartas extraidas
        val (nuevasCartasMano, nuevaBaraja) = baraja.pedirCartas(cartasDescartadas.size)
        // jugador recibe las cartas tomadas de la baraja
        val jugadorConNuevasCartas = nuevoJugador.recibirCartas(nuevasCartasMano)
        // actualizar jugadores para la UI
        val nuevosJugadores = jugadores.toMutableList()
        nuevosJugadores[1] = jugadorConNuevasCartas

        return this.copy(
            jugadores = nuevosJugadores.toList(),
            pilaDescarte = nuevaPilaDescarte,
            baraja = nuevaBaraja
        )
    }

    // cambia el estado "selecciona" de la carta entre "true" y "false"
    fun marcarCarta(carta: Carta): Juego {
        val jugadorActualizado = jugadores[1].marcarCartaEnMano(carta)
        val jugadoresActualizados = jugadores.toMutableList()
        jugadoresActualizados[1] = jugadorActualizado

        return this.copy(
            jugadores = jugadoresActualizados
        )
    }

    // passCartaToMesa: Devuelve un nuevo Juego con la carta jugada en la mesa
    fun passCartaToMesa(): Juego { // por cohesión esto debería recibir la 'Carta-Jugada' y el 'jugador-Objetivo' y usar el 'jugador-Activo'
        // val jugadorActual = this.jugadorActivo ?: return this
        val jugadorActual = jugadores[1]
        var jugadorActualizado =
            jugadorActual.jugarCarta() // debemos individualizar primero al jugador 'Activo' y al 'Objetivo'
        val cartaJugada = jugadorActual.entregarCartaJugada()
        // baraja actualizada y cartas extraidas
        val (nuevasCartasMano, barajaActualizada) = baraja.pedirCartas((maxCartasEnMano - jugadorActualizado.mano.cartas.size))
        // jugador recibe las cartas tomadas de la baraja
        jugadorActualizado = jugadorActualizado.recibirCartas(nuevasCartasMano)
        // actualizar jugadores para la UI
        val jugadoresActualizados = jugadores.toMutableList()
        jugadoresActualizados[1] = jugadorActualizado

        return this.copy(
            jugadores = jugadoresActualizados,
            baraja = barajaActualizada
        )
    }

    fun tomarCartaJugada(): Carta {
        val jugadorActivo = jugadores[1]
        val cartaJugada = jugadorActivo.entregarCartaJugada()

        return cartaJugada
    }

    fun llenarBaraja(): Juego {
        if (baraja.pila.size < maxCartasEnMano) {
            val (cartasPila, pilaDescarteActualizada) = pilaDescarte.tomarTodasLasCartas()
            val cartasPilaMutable = cartasPila.map { carta: Carta ->
                carta.copy(
                    estaSeleccionada = false,
                    esInmune = false,
                )
            }
            return this.copy(
                baraja = baraja.agregarCartas(cartasPilaMutable),
                pilaDescarte = pilaDescarteActualizada
            )
        } else {
            return this
        }
    }

    // cambiar jugador en turno (escalable)
    fun pasarTurno(): Jugador {
        if (jugadorActivo == null) {
            // jugador inincial por defecto
            return jugadores[1]
        } else {
            val index = jugadores.indexOf(jugadorActivo)
            return jugadores[ (index + 1) % jugadores.size ]
        }
    }
}