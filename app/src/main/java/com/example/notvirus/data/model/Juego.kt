package com.example.notvirus.data.model

class Juego(
    val jugadores: MutableList<Jugador> = mutableListOf(Jugador(nombre = "Jugador1"), Jugador(nombre = "Jugador2")),
    val baraja: Baraja = Baraja(),
    val pilaDescarte: PilaDescarte = PilaDescarte(),
    val jugador1Activo: Boolean = true,
    val jugador2Activo: Boolean = false,
    var jugadorActivo: Jugador? = null,
) {
    init {
        startJuego()
    }

    fun startJuego() {
        jugadores.forEach { jugador ->
            val nuevaMano = baraja.takeCartas(3)
            jugador.takeCartas(nuevaMano)
        }
        // se podría cambiar el 0 por un Random
        jugadores[0].isActive = true
        jugadorActivo = jugadores[0]
    }

    fun winner(): Unit {
        // mostrar ganador
    }

    fun leftInDeckCartas() {
        if (baraja.mazo.isEmpty()) {
            baraja.reassemble(pilaDescarte.empty())
        }
        // animacion
    }

    fun passCartasToPilaDescarte(): Unit {
        // pasar cartas de la mano a Pila descarte
        val cartasDescartadas = jugadorActivo!!.discardCartas()
        pilaDescarte.addCarta(cartasDescartadas)
    }

    fun passCartasToMesa(): Unit {
        // pasar cartas de la mano a mesa
        val cartaJugada = jugadorActivo!!.playCarta()

        when (cartaJugada.tipo) {
            CartaTipo.ORGANO -> addOrgano(cartaJugada)
            CartaTipo.MEDICINA -> addMedicina(cartaJugada)
            CartaTipo.VIRUS -> addVirus(cartaJugada)
            CartaTipo.TRATAMIENTO -> playTratamiento(cartaJugada)
        }
    }

    fun playTratamiento(cartaJugada: Carta) {

    }

    fun addVirus(cartaJugada: Carta) {

    }

    fun addOrgano(cartaJugada: Carta) {
        when (cartaJugada.color) {
            CartaColor.AMARILLO ->
                if (!existeOrgano(CartaColor.AMARILLO)) {
                    jugadorActivo!!.mesa.addAmarillo(cartaJugada)
                }

            CartaColor.VERDE ->
                if (!existeOrgano(CartaColor.VERDE)) {
                    jugadorActivo!!.mesa.addVerde(cartaJugada)
                }

            CartaColor.ROJO ->
                if (!existeOrgano(CartaColor.ROJO)) {
                    jugadorActivo!!.mesa.addRojo(cartaJugada)
                }

            CartaColor.AZUL ->
                if (!existeOrgano(CartaColor.AZUL)) {
                    jugadorActivo!!.mesa.addAzul(cartaJugada)
                }

            CartaColor.MULTICOLOR ->
                if (!existeOrgano(CartaColor.MULTICOLOR)) {
                    jugadorActivo!!.mesa.addMulticolor(cartaJugada)
                }

            else -> {}
        }
    }

    fun existeOrgano(color: CartaColor): Boolean {
        var tieneOrgano: Boolean = false
        when (color) {
            CartaColor.ROJO ->
                tieneOrgano = jugadorActivo!!.mesa.pilaRoja.any {
                    it.tipo == CartaTipo.ORGANO
                }

            CartaColor.AZUL ->
                tieneOrgano = jugadorActivo!!.mesa.pilaAzul.any {
                    it.tipo == CartaTipo.ORGANO
                }

            CartaColor.AMARILLO ->
                tieneOrgano = jugadorActivo!!.mesa.pilaAmarilla.any {
                    it.tipo == CartaTipo.ORGANO
                }

            CartaColor.VERDE ->
                tieneOrgano = jugadorActivo!!.mesa.pilaVerde.any {
                    it.tipo == CartaTipo.ORGANO
                }

            CartaColor.MULTICOLOR ->
                tieneOrgano = jugadorActivo!!.mesa.pilaMulticolor.any {
                    it.tipo == CartaTipo.ORGANO
                }

            else -> {}
        }
        return tieneOrgano
    }

    fun addMedicina(cartaJugada: Carta) {

    }

    fun passTurn(): Unit {
        // cambiar jugador Activo
        val index = jugadores.indexOf(jugadorActivo)
        jugadores[index].isActive = false
        jugadores[(index + 1) % jugadores.size].isActive = true
        jugadorActivo = jugadores[(index + 1) % jugadores.size]
    }
}