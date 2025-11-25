package com.example.notvirus.data.model

import java.util.UUID

data class Jugador(
    val nombre: String = "Player X", // nombre de Usuario
    val isActive: Boolean = false,
    val mano: Mano = Mano(),
    val mesa: Mesa = Mesa(),
    val id:String = UUID.randomUUID().toString()
) {
    fun recibirCartasToMano(nuevasCartas: List<Carta>): Jugador {
        // Metodo para "recibir cartas" -> "agregar a la mano" y devolver el Jugador con la mano actualizada
        println("Jugador.recibirCartasToMano()")
        val manoActualizada = mano.agregarCartas(nuevasCartas = nuevasCartas)
        return this.copy(
            mano = manoActualizada
        )
    }

    fun descartarCartas(): Pair<List<Carta>, Jugador> {
        // Metodo para "descartar cartas" y devolver el Jugador con la mano actualizada
        println("Jugador.descartarCartas()")
        // jugador separa las cartas a descartar y mantiene en mano las NO seleccionadas
        val (cartasSeleccionadas, manoActualizada) = mano.tomarCartasSeleccionadas()
        println("cartasSeleccionadas.size: ${cartasSeleccionadas.size}")
        println("manoActualizada.size: ${manoActualizada.cartas.size}")
        val jugadorActualizado = this.copy(
            mano = manoActualizada
        )
        // jugador pasa las cartas para descartar
        return Pair(
            cartasSeleccionadas,
            jugadorActualizado,
        )
    }

    fun marcarCartaEnMano(carta: Carta): Jugador {
        println("Jugador.marcarCartaEnMano()")
        val manoAct = mano.selectCarta(carta)
        return this.copy(
            mano = manoAct
        )
    }

    fun agregaCartaToMesa(carta:Carta): Jugador{
        println("Jugador.agregaCartaToMesa()")
        val mesaActualizada = mesa.agregarToPila(carta)
        return this.copy(
            mesa = mesaActualizada
        )
    }

    // jugarCarta en desuso
    fun jugarCarta(): Jugador {
        // mover esta lógica a "Juego"
        // qué carta se pretende jugar
        val (cartas, manoActualizada) = mano.tomarCartasSeleccionadas()
        val cartaSeleccionada = cartas[0]
        var mesaAct: Mesa = this.mesa
        var manoAct: Mano = this.mano
        // ¿puede jugarse? - depende del tipo
        when (cartaSeleccionada.tipo) {
            CartaTipo.ORGANO ->
                if (!existeOrgano(color = cartaSeleccionada.color)) {
                    mesaAct = addOrgano(cartaJugada = cartaSeleccionada)
                    manoAct = manoActualizada
                } else {
                    // lanzar mensaje (snackBar)
                }

            CartaTipo.VIRUS ->
                if (existeOrgano(color = cartaSeleccionada.color)) {
                    mesaAct = addVirus(cartaJugada = cartaSeleccionada)
                    manoAct = manoActualizada
                } else {
                    // lanzar mensaje (snackBar)
                }

            CartaTipo.MEDICINA ->
                if (existeOrgano(color = cartaSeleccionada.color)) {
                    mesaAct = addMedicina(cartaJugada = cartaSeleccionada)
                    manoAct = manoActualizada
                } else {
                    // lanzar mensaje (snackBar)
                }

            else -> {
                println("Accion de carta no implementada")
            }
        }
        return this.copy(
            mesa = mesaAct,
            mano = manoAct
        )
    }

    fun entregarCartaJugada(): Carta {
        println("Jugador.entregarCartaJugada()")
        val (cartas, _) = mano.tomarCartasSeleccionadas()
        val cartaJugada = cartas[0]

        return cartaJugada
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
            mesa.pilas[colorPila]!!.any { it.tipo == CartaTipo.VIRUS }
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
/*
// TRATAMIENTOS:Start
    private fun playTratamiento(cartaJugada: Carta): Jugador {
        // tratamiento activa su efecto particular
        // va directo a la pila de descarte
        return this.copy(

        )
    }
// TRATAMIENTOS:End
*/
}