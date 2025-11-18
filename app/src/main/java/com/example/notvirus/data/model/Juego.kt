package com.example.notvirus.data.model

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
        val nuevaPilaDescarte = pilaDescarte.addCarta(cartasDescartadas)
        val (nuevaBaraja, nuevasCartasMano) = baraja.takeCartas(cartasDescartadas.size)
        val jugadorConNuevasCartas = nuevoJugador.takeCartas(nuevasCartasMano)
        val nuevosJugadores = jugadores.toMutableList()
        nuevosJugadores[1] = jugadorConNuevasCartas

        return this.copy(
            jugadores = nuevosJugadores.toList(),
            pilaDescarte = nuevaPilaDescarte,
            baraja = nuevaBaraja
        )
    }

    fun marcarCarta(index: Int): Juego{
        val jugadorActualizado = jugadores[1].actualizarMano(index)
        val jugadoresAct = jugadores.toMutableList()
        jugadoresAct[1] = jugadorActualizado

        return this.copy(
            jugadores = jugadoresAct
        )
    }

    // passCartasToMesa: Devuelve un nuevo Juego con la carta jugada en la mesa
    fun passCartasToMesa(): Juego {
        val jugadorActual = this.jugadorActivo ?: return this // No hay jugador activo
        val (nuevoJugador, cartaJugada) = jugadorActual.playCarta() // <-- Desestructura aquí

        // Ahora puedes usar `cartaJugada` como una Carta
        val nuevoJuegoConCartaJugada = when (cartaJugada.tipo) {
            CartaTipo.ORGANO -> this.addOrgano(cartaJugada, nuevoJugador)
            CartaTipo.MEDICINA -> this.addMedicina(cartaJugada, nuevoJugador)
            CartaTipo.VIRUS -> this.addVirus(cartaJugada, nuevoJugador)
            CartaTipo.TRATAMIENTO -> this.playTratamiento(cartaJugada, nuevoJugador)
            CartaTipo.NULL -> this.copy(jugadores = jugadores.map {
                if (it.nombre == nuevoJugador.nombre) nuevoJugador else it
            })
        }

        val nuevosJugadores = nuevoJuegoConCartaJugada.jugadores.map {
            if (it.nombre == nuevoJugador.nombre) nuevoJugador else it
        }

        return nuevoJuegoConCartaJugada.copy(jugadores = nuevosJugadores)
    }

    private fun addOrgano(cartaJugada: Carta, jugadorActual: Jugador): Juego {
        val nuevaMesa = when (cartaJugada.color) {
            CartaColor.AMARILLO ->
                if (!existeOrgano(CartaColor.AMARILLO, jugadorActual)) {
                    jugadorActual.mesa.addAmarillo(cartaJugada)
                } else jugadorActual.mesa

            CartaColor.VERDE ->
                if (!existeOrgano(CartaColor.VERDE, jugadorActual)) {
                    jugadorActual.mesa.addVerde(cartaJugada)
                } else jugadorActual.mesa

            CartaColor.ROJO ->
                if (!existeOrgano(CartaColor.ROJO, jugadorActual)) {
                    jugadorActual.mesa.addRojo(cartaJugada)
                } else jugadorActual.mesa

            CartaColor.AZUL ->
                if (!existeOrgano(CartaColor.AZUL, jugadorActual)) {
                    jugadorActual.mesa.addAzul(cartaJugada)
                } else jugadorActual.mesa

            CartaColor.MULTICOLOR ->
                if (!existeOrgano(CartaColor.MULTICOLOR, jugadorActual)) {
                    jugadorActual.mesa.addMulticolor(cartaJugada)
                } else jugadorActual.mesa

            else -> jugadorActual.mesa
        }

        val nuevoJugador = jugadorActual.copy(mesa = nuevaMesa)
        val nuevosJugadores = this.jugadores.map {
            if (it.nombre == jugadorActual.nombre) nuevoJugador else it
        }

        return this.copy(jugadores = nuevosJugadores)
    }

    private fun addMedicina(cartaJugada: Carta, jugadorActual: Jugador): Juego {
        // Implementar lógica de medicina
        return this
    }

    private fun addVirus(cartaJugada: Carta, jugadorActual: Jugador): Juego {
        // Implementar lógica de virus
        return this
    }

    private fun playTratamiento(cartaJugada: Carta, jugadorActual: Jugador): Juego {
        // Implementar lógica de tratamiento
        return this
    }

    private fun existeOrgano(color: CartaColor, jugador: Jugador): Boolean {
        return when (color) {
            CartaColor.ROJO -> jugador.mesa.pilaRoja.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.AZUL -> jugador.mesa.pilaAzul.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.AMARILLO -> jugador.mesa.pilaAmarilla.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.VERDE -> jugador.mesa.pilaVerde.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.MULTICOLOR -> jugador.mesa.pilaMulticolor.any { it.tipo == CartaTipo.ORGANO }
            else -> false
        }
    }

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