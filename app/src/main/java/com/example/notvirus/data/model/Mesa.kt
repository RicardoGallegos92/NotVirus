package com.example.notvirus.data.model

data class Mesa(
    val pilas: Map<CartaColor, MutableList<Carta>> = mapOf(
        CartaColor.AMARILLO to mutableListOf(),
        CartaColor.AZUL to mutableListOf(),
        CartaColor.ROJO to mutableListOf(),
        CartaColor.VERDE to mutableListOf(),
        CartaColor.MULTICOLOR to mutableListOf(),
    ),
    val turnosParaGanar: Int = 4, // int = [ 0, 4 ]
) {
    // Devuelve nueva instancia de Mesa
    fun agregarToPila(nuevaCarta: Carta, colorPila: CartaColor? = null): Mesa {
        val pilasCopia = pilas.toMutableMap()
        if (colorPila != null) {
            pilasCopia[colorPila]!!.add(nuevaCarta)
        } else {
            pilasCopia[nuevaCarta.color]!!.add(nuevaCarta)
        }
        return this.copy(
            pilas = pilasCopia,
            turnosParaGanar = calcularTurnosParaGanar(pilasCopia = pilasCopia)
        )
    }

    fun quitarDePila(cartasParaQuitar: List<Carta>, colorPila: CartaColor): Mesa {
        val pilasCopia = pilas.toMutableMap()
        pilasCopia[colorPila]!!.removeAll(cartasParaQuitar)
        return this.copy(
            pilas = pilasCopia,
            turnosParaGanar = calcularTurnosParaGanar(pilasCopia = pilasCopia)
        )
    }

    fun calcularTurnosParaGanar(pilasCopia:Map<CartaColor, MutableList<Carta>>): Int {
        // Lógica para calcular el nuevo valor
        var turnosParaGanar = 4 // Coloca tu lógica aquí
        pilasCopia.forEach { (color, pila) ->
            pila.forEach { carta: Carta ->
                turnosParaGanar += when (carta.tipo) {
                    CartaTipo.ORGANO -> { -1 }
                    CartaTipo.VIRUS -> { 1 }
//                    CartaTipo.MEDICINA -> { 0 } // NO mueve el contador
                    else -> { 0 }
                }
            }
        }
        return turnosParaGanar
    }
}