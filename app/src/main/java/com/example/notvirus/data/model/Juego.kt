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
        val jugadoresConCartas = jugadores.map { jugador ->
            val (cartasParaJugador, nuevaBaraja) = barajaActual.pedirCartas(maxCartasEnMano) // <-- solicitar cartas para la mano
            barajaActual = nuevaBaraja // Actualiza la baraja para el siguiente jugador
            jugador.recibirCartasToMano(cartasParaJugador) // <-- Pasa la lista de cartas
        }

        return this.copy(
            jugadores = jugadoresConCartas,
            baraja = barajaActual, // <-- Baraja-Actualizada
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
            // vacio -> no ganador
            Pair(false, null)
        } else {
            Pair(true, jugadorGanador[0])
        }
    }

    fun jugarCarta(): Juego {
        val cartaJugada: Carta = tomarCartaJugada()
        // distinguir entre TRATAMIENTO y otras
        when(cartaJugada.tipo){
            CartaTipo.TRATAMIENTO -> {
                // activar Efecto
                // quitar 'Carta' de la 'Mano'
                // enviar 'Carta' a 'PilaDescarte'
                return descartarDesdeMano()
            }
            else -> {
                pasarCartaToMesa(
                    cartaJugada = cartaJugada,
                    jugadorObjetivo = pasarTurno() // (provisorio) -> se debe agregar funcion para seleccionar objetivo
                )
            }
        }

        return this.copy(

        )
    }
    fun pasarCartaToMesa(cartaJugada: Carta, jugadorObjetivo:Jugador): Juego {
        // pasarCartaToMesa: Devuelve un 'Juego-Actualizado':
        // por cohesión esto debería recibir la 'cartaJugada' y el 'jugadorObjetivo' y usar el 'jugadorActivo'

        // pasar la 'cartaJugada' a la mesa del 'jugadorObjetivo'
        val jugadorObjetivoActualizado = jugadorObjetivo.agregaCartaToMesa(cartaJugada)
        // quitar la 'cartaJugada' de la mano del 'jugadorActivo'
        var (_,jugadorActivoActualizado) = jugadorActivo!!.descartarCartas()
        // pedir 'cartaNueva' para la mano del 'jugadorActivo'
        val (nuevasCartasMano, barajaActualizada) = baraja.pedirCartas((maxCartasEnMano - jugadorActivoActualizado.mano.cartas.size))
        // pasar la 'cartaNueva' al 'jugadorActivo'
        jugadorActivoActualizado = jugadorActivoActualizado.recibirCartasToMano(nuevasCartasMano)

        // actualizar jugadores Activo y Objetivo para la UI
        val jugadoresActualizados = jugadores.toMutableList()

        jugadoresActualizados[ jugadoresActualizados.indexOf(jugadorActivo) ] = jugadorActivoActualizado
        jugadoresActualizados[ jugadoresActualizados.indexOf(jugadorObjetivo) ] = jugadorObjetivoActualizado

        return this.copy(
            jugadores = jugadoresActualizados,
            baraja = barajaActualizada
        )
    }

    fun descartarDesdeMano(): Juego {
        println("Juego.passCartasToPilaDescarte()")
        // no hace falta lógica extra, las cartas pasan de la mano a la pila
        val (cartasDescartadas, jugadorActivoActualizado) = jugadorActivo!!.descartarCartas()
        // baraja actualizada y cartas extraidas
        val (nuevasCartasMano, barajaActualizada) = baraja.pedirCartas(cartasDescartadas.size)
        // jugador recibe las cartas tomadas de la baraja
        val jugadorConNuevasCartas = jugadorActivoActualizado.recibirCartasToMano(nuevasCartasMano)
        // actualizar jugadores para la UI
        val jugadoresActualizados = jugadores.toMutableList()

        jugadoresActualizados[ jugadoresActualizados.indexOf(jugadorActivo) ] = jugadorConNuevasCartas

        return this.copy(
            jugadores = jugadoresActualizados,
            pilaDescarte = pilaDescarte.agregarCartas(cartasDescartadas),
            baraja = barajaActualizada,
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

    fun tomarCartaJugada(): Carta {
        val cartaJugada = jugadorActivo!!.entregarCartaJugada()

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