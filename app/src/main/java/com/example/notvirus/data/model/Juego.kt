package com.example.notvirus.data.model

import java.nio.file.Files.size

data class Juego(
    val jugadores: List<Jugador>,
    val baraja: Baraja,
    val pilaDescarte: PilaDescarte,
    val jugador1Activo: Boolean,
    val jugador2Activo: Boolean,
    val jugadorActivo: Jugador?,
) {
    constructor() : this(
        jugadores = listOf(Jugador(nombre = "CPU"), Jugador(nombre = "Jugador1")),
        baraja = Baraja(),
        pilaDescarte = PilaDescarte(),
        jugador1Activo = true,
        jugador2Activo = false,
        jugadorActivo = null
    )

    // startJuego: Devuelve un nuevo Juego con manos repartidas y primer jugador activo
    fun startJuego(): Juego {
        var barajaActual = this.baraja // Mantén un registro de la baraja actualizada
        val nuevosJugadores = jugadores.map { jugador ->
            val (nuevaBaraja, cartasTomadas) = barajaActual.takeCartas(3) // <-- solicitar cartas para la mano
            barajaActual = nuevaBaraja // Actualiza la baraja para el siguiente jugador
            jugador.takeCartas(cartasTomadas) // <-- Pasa la lista de cartas
        }.toMutableList()

        // Activar primer jugador
        val jugadorInicial = nuevosJugadores[0]
        val jugadorConEstadoActivo = jugadorInicial.copy(isActive = true)
        nuevosJugadores[0] = jugadorConEstadoActivo

        return this.copy(
            jugadores = nuevosJugadores,
            baraja = barajaActual, // <-- Asegúrate de usar la baraja actualizada
            jugadorActivo = jugadorConEstadoActivo,
            jugador1Activo = true // <-- Ajusta según tu lógica, puede ser solo una bandera
            // jugador2Activo = false // <-- Opcionalmente actualiza también este
        )
    }

    fun winner(): Juego {
        // Lógica para determinar ganador
        // Devolver nuevo estado si aplica
        return this // Placeholder
    }

    fun leftInDeckCartas(): Juego {
        if (baraja.mazo.isEmpty()) {
            val nuevasCartasReensambladas = pilaDescarte.empty()
            val nuevaBaraja = baraja.reassemble(nuevasCartasReensambladas)
            return this.copy(baraja = nuevaBaraja)
        }
        return this
    }

    // passCartasToPilaDescarte: Devuelve un nuevo Juego con cartas descartadas
    fun passCartasToPilaDescarte(): Juego {
        println("Juego.passCartasToPilaDescarte()")
        val (nuevoJugador, cartasDescartadas) = jugadores[1].discardCartas()
        // pila descarte actualizada
        val nuevaPilaDescarte = pilaDescarte.addCarta(cartasDescartadas)
        // baraja actualizada y cartas extraidas
        val (nuevaBaraja, nuevasCartasMano) = baraja.takeCartas(cartasDescartadas.size)
        // jugador recibe las cartas tomadas de la baraja
        val jugadorConNuevasCartas = nuevoJugador.takeCartas(nuevasCartasMano)
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
    fun marcarCarta(index: Int): Juego {
        val jugadorActualizado = jugadores[1].actualizarMano(index)
        val jugadoresAct = jugadores.toMutableList()
        jugadoresAct[1] = jugadorActualizado

        return this.copy(
            jugadores = jugadoresAct
        )
    }

    // passCartaToMesa: Devuelve un nuevo Juego con la carta jugada en la mesa
    fun passCartaToMesa(): Juego {
        // val jugadorActual = this.jugadorActivo ?: return this
        val jugadorActual = jugadores[1]
        //val (nuevoJugador, cartaJugada) = jugadorActual.playCarta()
        val jugadorActualizado = jugadorActual.jugarCarta()
        // baraja actualizada y cartas extraidas
        val (barajaActualizada, nuevasCartasMano) = baraja.takeCartas((3 - jugadorActualizado.mano.cartas.size) )
        // jugador recibe las cartas tomadas de la baraja
        val jugadorConManoLlena = jugadorActualizado.takeCartas(nuevasCartasMano)
        // actualizar jugadores para la UI
        val jugadoresActualizados = jugadores.toMutableList()
        jugadoresActualizados[1] = jugadorConManoLlena

        return this.copy(
            jugadores = jugadoresActualizados,
            baraja = barajaActualizada
        )
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