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
                if ( existeOrgano(color = carta.color) ) {
                    mesaAct = addVirus(cartaJugada = carta)
                    manoAct = manoActualizada
                } else {
                    // lanzar mensaje (snackBar)
                }

            CartaTipo.MEDICINA ->
                if ( existeOrgano(color = carta.color) ) {
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
    private fun existeOrgano(color: CartaColor): Boolean {
        // verifica si la pila ya contiene un órgano
        return mesa.pilas[color]!!.any { it.tipo == CartaTipo.ORGANO }
    }

    private fun addOrgano(cartaJugada: Carta): Mesa {
        val mesaActualizada = if (!existeOrgano(cartaJugada.color)) {
            mesa.agregarToPila(cartaJugada)
        } else {
            mesa
        }
        return mesaActualizada
    }

// ORGANO :End
// MEDICINA :Start
    private fun existeMedicina(color: CartaColor, colorPila: CartaColor? = null): Boolean {
        return if (colorPila != null) {
            mesa.pilas[colorPila]!!.any { it.tipo == CartaTipo.MEDICINA }
        } else {
            mesa.pilas[color]!!.any { it.tipo == CartaTipo.MEDICINA }
        }
    }

    private fun addMedicina(cartaJugada: Carta, colorPila: CartaColor? = null): Mesa {
        val mesaActualizada = if (existeOrgano(cartaJugada.color)) {
            mesa.agregarToPila(cartaJugada, colorPila)
        } else {
            mesa
        }
        return mesaActualizada
    }

// MEDICINA :End
// VIRUS:Start
    private fun existeVirus(color: CartaColor, colorPila: CartaColor? = null): Boolean {
    return if (colorPila != null) {
            mesa.pilas[colorPila]!!.any { it.tipo == CartaTipo.VIRUS}
        } else {
            mesa.pilas[color]!!.any { it.tipo == CartaTipo.VIRUS }
        }
    }
    private fun addVirus(cartaJugada: Carta): Mesa {
        val mesaActualizada = if (existeOrgano(cartaJugada.color)) {
            mesa.agregarToPila(cartaJugada)
        } else {
            mesa
        }
        return mesaActualizada
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