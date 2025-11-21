package com.example.notvirus.data.model

import java.nio.file.Files.size

data class Juego(
    val jugadores: List<Jugador>,
    val baraja: Baraja,
    val pilaDescarte: PilaDescarte,

    val maxCartasEnMano: Int,
    val jugador1Activo: Boolean,
    val jugador2Activo: Boolean,
    val jugadorActivo: Jugador?,
) {
    constructor() : this(
        jugadores = listOf(Jugador(nombre = "CPU"), Jugador(nombre = "Jugador1")),
        baraja = Baraja(),
        pilaDescarte = PilaDescarte(),

        maxCartasEnMano = 3,
        jugador1Activo = true,
        jugador2Activo = false,
        jugadorActivo = null
    )

    // startJuego: Devuelve un nuevo Juego con manos repartidas y primer jugador activo
    fun startJuego(): Juego {
        var barajaActual = this.baraja // Mantén un registro de la baraja actualizada
        val nuevosJugadores = jugadores.map { jugador ->
            val (cartasParaJugador, nuevaBaraja) = barajaActual.pedirCartas(maxCartasEnMano) // <-- solicitar cartas para la mano
            barajaActual = nuevaBaraja // Actualiza la baraja para el siguiente jugador
            jugador.tomaCartas(cartasParaJugador) // <-- Pasa la lista de cartas
        }.toMutableList()
        /*
                // Activar primer jugador
                val jugadorInicial = nuevosJugadores[0]
                val jugadorConEstadoActivo = jugadorInicial.copy(isActive = true)
                nuevosJugadores[0] = jugadorConEstadoActivo
        */
        return this.copy(
            jugadores = nuevosJugadores.toList(),
            baraja = barajaActual, // <-- Asegúrate de usar la baraja actualizada
            //jugadorActivo = jugadorConEstadoActivo,
            jugador1Activo = true // <-- Ajusta según tu lógica, puede ser solo una bandera
            // jugador2Activo = false // <-- Opcionalmente actualiza también este
        )
    }

    fun winner(): Juego {
        // Lógica para determinar ganador
        // Devolver nuevo estado si aplica
        return this.copy() // Placeholder
    }

    // passCartasToPilaDescarte: Devuelve un nuevo Juego con cartas descartadas
    fun passCartasToPilaDescarte(): Juego {
        println("Juego.passCartasToPilaDescarte()")
        val (nuevoJugador, cartasDescartadas) = jugadores[1].discardCartas()
        // pila descarte actualizada
        val nuevaPilaDescarte = pilaDescarte.agregarCartas(cartasDescartadas)
        // baraja actualizada y cartas extraidas
        val (nuevasCartasMano, nuevaBaraja) = baraja.pedirCartas(cartasDescartadas.size)
        // jugador recibe las cartas tomadas de la baraja
        val jugadorConNuevasCartas = nuevoJugador.tomaCartas(nuevasCartasMano)
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
    fun passCartaToMesa(): Juego {
        // val jugadorActual = this.jugadorActivo ?: return this
        val jugadorActual = jugadores[1]
        //val (cartaJugada, jugadorActualizado) = jugadorActual.playCarta()

        var jugadorActualizado = jugadorActual.jugarCarta()
        // baraja actualizada y cartas extraidas
        val (nuevasCartasMano, barajaActualizada) = baraja.pedirCartas((maxCartasEnMano - jugadorActualizado.mano.cartas.size))
        // jugador recibe las cartas tomadas de la baraja
        jugadorActualizado = jugadorActualizado.tomaCartas(nuevasCartasMano)
        // actualizar jugadores para la UI
        val jugadoresActualizados = jugadores.toMutableList()
        jugadoresActualizados[1] = jugadorActualizado

        return this.copy(
            jugadores = jugadoresActualizados,
            baraja = barajaActualizada
        )
    }

    fun llenarBaraja(): Juego {
        if (baraja.pila.size < maxCartasEnMano) {
            val (cartasPila, pilaDescarteActualizada) = pilaDescarte.tomarTodasLasCartas()
            return this.copy(
                baraja = baraja.agregarCartas(cartasPila),
                pilaDescarte = pilaDescarteActualizada
            )
        } else {
            return this
        }
    }

    // cambiar jugador en turno (escalable)
    fun passTurn(): Juego {
        val currentIndex = jugadores.indexOf(this.jugadorActivo)
        if (currentIndex == -1) return this // No hay jugador activo

        val nuevosJugadores = jugadores.mapIndexed { index, jugador ->
            jugador.copy(isActive = index == (currentIndex + 1) % jugadores.size)
        }

        val nuevoJugadorActivo = nuevosJugadores[(currentIndex + 1) % jugadores.size]

        return this.copy(
            jugadores = nuevosJugadores,
            jugadorActivo = nuevoJugadorActivo
        )
    }
}