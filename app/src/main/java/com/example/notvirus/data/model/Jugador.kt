package com.example.notvirus.data.model

data class Jugador(
    val nombre: String = "Player X", // nombre de Usuario
    val isActive: Boolean = false,
    val mano: Mano = Mano(),
    val mesa: Mesa = Mesa(),
) {
    fun tomaCartas(nuevasCartas: List<Carta>): Jugador {
        // Metodo para "tomar cartas" y devolver el Jugador con la mano actualizada
        println("Jugador.tomaCartas()")
        val manoActualizada = mano.agregarCartas(nuevasCartas = nuevasCartas)
        return this.copy(
            mano = manoActualizada
        )
    }

    fun discardCartas(): Pair<Jugador, List<Carta>> {
        // Metodo para "descartar cartas" y devolver el Jugador con la mano actualizada
        println("Jugador.discardCartas()")
        // jugador separa las cartas a descartar y mantiene en mano las NO seleccionadas
        val (cartasDescartadas, manoActualizada) = mano.tomarCartasSeleccionadas()
        val nuevoJugador = this.copy(
            mano = manoActualizada
        )
        // jugador pasa las cartas para descartar
        return Pair(
            nuevoJugador,
            cartasDescartadas
        )
    }

    fun marcarCartaEnMano(carta: Carta): Jugador {
        val manoAct = mano.selectCarta(carta)
        return this.copy(
            mano = manoAct
        )
    }

    fun jugarCarta(): Jugador {
        // qué carta se pretende jugar
        val (cartaSeleccionada, manoActualizada) = mano.tomarCartasSeleccionadas()
        val carta = cartaSeleccionada[0]
        // ¿puede jugarse? - depende del tipo
        var mesaAct: Mesa = this.mesa
        var manoAct: Mano = this.mano
        when (carta.tipo) {
            CartaTipo.ORGANO ->
                if (!existeOrgano(color = carta.color)) {
                    mesaAct = addOrgano(cartaJugada = carta)
                    manoAct = manoActualizada
                } else {
                    // lanzar mensaje (snackBar)
                }

            CartaTipo.VIRUS ->
                if (existeOrgano(color = carta.color)
                    || existeMedicina(color = carta.color)
                ) {
                    mesaAct = addVirus(cartaJugada = carta)
                    manoAct = manoActualizada
                } else {
                    // lanzar mensaje (snackBar)
                }

            CartaTipo.MEDICINA ->
                if (existeOrgano(color = carta.color)
                    || existeVirus(color = carta.color)
                ) {
                    mesaAct = addMedicina(cartaJugada = carta)
                    manoAct = manoActualizada
                } else {
                    // lanzar mensaje (snackBar)
                }

            CartaTipo.TRATAMIENTO ->
                playTratamiento(carta)

            else -> {
                println("Accion de carta no implementada")
            }
        }
        return this.copy(
            mesa = mesaAct,
            mano = manoAct
        )
    }

    // ORGANO :Start
    private fun addOrgano(cartaJugada: Carta): Mesa {
        val nuevaMesa = when (cartaJugada.color) {
            CartaColor.AMARILLO ->
                if (!existeOrgano(CartaColor.AMARILLO)) {
                    mesa.agregarToAmarillo(cartaJugada)
                } else mesa

            CartaColor.VERDE ->
                if (!existeOrgano(CartaColor.VERDE)) {
                    mesa.agregarToVerde(cartaJugada)
                } else mesa

            CartaColor.ROJO ->
                if (!existeOrgano(CartaColor.ROJO)) {
                    mesa.agregarToRojo(cartaJugada)
                } else mesa

            CartaColor.AZUL ->
                if (!existeOrgano(CartaColor.AZUL)) {
                    mesa.agregarToAzul(cartaJugada)
                } else mesa

            CartaColor.MULTICOLOR ->
                if (!existeOrgano(CartaColor.MULTICOLOR)) {
                    mesa.agregarToMulticolor(cartaJugada)
                } else mesa

            else -> mesa
        }
        return nuevaMesa
    }

    private fun existeOrgano(color: CartaColor): Boolean {
        // verifica si la pila ya está ocupada por un órgano
        return when (color) {
            CartaColor.ROJO -> mesa.pilaRoja.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.AZUL -> mesa.pilaAzul.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.VERDE -> mesa.pilaVerde.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.AMARILLO -> mesa.pilaAmarilla.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.MULTICOLOR -> mesa.pilaMulticolor.any { it.tipo == CartaTipo.ORGANO }
            else -> false
        }
    }

// ORGANO :End
// MEDICINA :Start
    private fun addMedicina(cartaJugada: Carta): Mesa {
        val nuevaMesa = when (cartaJugada.color) {
            CartaColor.AMARILLO ->
                if (existeOrgano(CartaColor.AMARILLO)) {
                    mesa.agregarToAmarillo(cartaJugada)
                } else mesa

            CartaColor.VERDE ->
                if (existeOrgano(CartaColor.VERDE)) {
                    mesa.agregarToVerde(cartaJugada)
                } else mesa

            CartaColor.ROJO ->
                if (existeOrgano(CartaColor.ROJO)) {
                    mesa.agregarToRojo(cartaJugada)
                } else mesa

            CartaColor.AZUL ->
                if (existeOrgano(CartaColor.AZUL)) {
                    mesa.agregarToAzul(cartaJugada)
                } else mesa

            CartaColor.MULTICOLOR ->
                if (existeOrgano(CartaColor.MULTICOLOR)) {
                    mesa.agregarToMulticolor(cartaJugada)
                } else mesa

            else -> mesa
        }
        return nuevaMesa
    }

    private fun existeMedicina(color: CartaColor): Boolean {
        return false
    }

// MEDICINA :End
// VIRUS:Start
    private fun addVirus(cartaJugada: Carta): Mesa {
        val nuevaMesa = when (cartaJugada.color) {
            CartaColor.AMARILLO ->
                if (existeOrgano(CartaColor.AMARILLO)
                    || existeMedicina(CartaColor.AMARILLO)
                ) {
                    mesa.agregarToAmarillo(cartaJugada)
                } else mesa

            CartaColor.VERDE,
                ->
                if (existeOrgano(CartaColor.VERDE)
                    || existeMedicina(CartaColor.VERDE)
                ) {
                    mesa.agregarToVerde(cartaJugada)
                } else mesa

            CartaColor.ROJO ->
                if (existeOrgano(CartaColor.ROJO)
                    || existeMedicina(CartaColor.ROJO)
                ) {
                    mesa.agregarToRojo(cartaJugada)
                } else mesa

            CartaColor.AZUL ->
                if (existeOrgano(CartaColor.AZUL)
                    || existeMedicina(CartaColor.AZUL)
                ) {
                    mesa.agregarToAzul(cartaJugada)
                } else mesa

            CartaColor.MULTICOLOR ->
                if (existeOrgano(CartaColor.MULTICOLOR)
                    || existeMedicina(CartaColor.MULTICOLOR)
                ) {
                    mesa.agregarToMulticolor(cartaJugada)
                } else mesa

            else -> mesa
        }
        return nuevaMesa
    }

    private fun existeVirus(color: CartaColor): Boolean {
        return false
    }
// VIRUS:End

// TRATAMIENTOS:Start
    private fun playTratamiento(cartaJugada: Carta): Jugador {
        // tratamiento activa su efecto particular
        // va directo a la pila de descarte
        return this.copy(

        )
    }
// TRATAMIENTOS:End
}