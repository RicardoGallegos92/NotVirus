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
        val cartasSeleccionadas = mano.tomarCartasSeleccionadas()
        val manoActualizada = mano.quitarCartasSeleccionadas()
//        println("cartasSeleccionadas.size: ${cartasSeleccionadas.size}")
//        println("manoActualizada.size: ${manoActualizada.cartas.size}")
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
        val manoAct = mano.seleccionarCarta(carta)
        return this.copy(
            mano = manoAct
        )
    }

    fun agregaCartaToMesa(carta:Carta): Jugador{
        println("Jugador.agregaCartaToMesa()")
        // jugador recibe la carta y la pasa a su mesa (pila indicada)
        // solo si tiene menos de 3 cartas

        val mesaActualizada = mesa.agregarToPila(carta)
        return this.copy(
            mesa = mesaActualizada
        )

    }

    fun entregarCartaJugada(): Carta {
        println("Jugador.entregarCartaJugada()")
        val cartas = mano.tomarCartasSeleccionadas()
        val cartaJugada = cartas[0]

        return cartaJugada
    }

    fun esInmune(color: CartaColor, colorPila: CartaColor? = null): Boolean{
        return mesa.pilas[color]?.any { it.esInmune } == true
    }

// ORGANO :Start
    fun existeOrgano(color: CartaColor): Boolean {
        // verifica si la pila ya contiene un órgano
        return mesa.pilas[color]!!.any { it.tipo == CartaTipo.ORGANO }
    }

    fun addOrgano(cartaJugada: Carta): Mesa {
        val mesaActualizada = if (!existeOrgano(cartaJugada.color)) {
            mesa.agregarToPila(cartaJugada)
        } else {
            mesa
        }
        return mesaActualizada
    }

// ORGANO :End
// MEDICINA :Start
    fun existeMedicina(color: CartaColor, colorPila: CartaColor? = null): Boolean {
        return if (colorPila != null) {
            mesa.pilas[colorPila]!!.any { it.tipo == CartaTipo.MEDICINA }
        } else {
            mesa.pilas[color]!!.any { it.tipo == CartaTipo.MEDICINA }
        }
    }

    fun addMedicina(cartaJugada: Carta, colorPila: CartaColor? = null): Mesa {
        val mesaActualizada = if (existeOrgano(cartaJugada.color)) {
            mesa.agregarToPila(cartaJugada, colorPila)
        } else {
            mesa
        }
        return mesaActualizada
    }

// MEDICINA :End
// VIRUS:Start
    fun existeVirus(color: CartaColor, colorPila: CartaColor? = null): Boolean {
        return if (colorPila != null) {
            mesa.pilas[colorPila]!!.any { it.tipo == CartaTipo.VIRUS }
        } else {
            mesa.pilas[color]!!.any { it.tipo == CartaTipo.VIRUS }
        }
    }

    fun addVirus(cartaJugada: Carta): Mesa {
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
    fun playTratamiento(cartaJugada: Carta): Jugador {
        // tratamiento activa su efecto particular
        // va directo a la pila de descarte
        return this.copy(

        )
    }
// TRATAMIENTOS:End
*/
}