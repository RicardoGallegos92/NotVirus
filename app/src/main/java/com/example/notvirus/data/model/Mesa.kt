package com.example.notvirus.data.model

import kotlin.collections.filter

data class Mesa(
    val pilas: List<PilaDeColor> = listOf(
        PilaDeColor(CartaColor.AMARILLO),
        PilaDeColor(CartaColor.AZUL),
        PilaDeColor(CartaColor.ROJO),
        PilaDeColor(CartaColor.VERDE),
        PilaDeColor(CartaColor.MULTICOLOR),
    ),
    val turnosParaGanar: Int = 4, // int = [ 0, 4 ]
    val maxCartasEnPila: Int = 3,
) {
    /**
     * @param nuevaCarta Carta para agregar a la Mesa
     * @param colorPilaObjetivo indica el color de la pila en el que debe agregarse la [nuevaCarta]
     * @return Mesa actualizada con la [carta] agregada
      */
    fun agregarCarta(nuevaCarta: Carta, colorPilaObjetivo: CartaColor): Mesa {
        val TAG = "Mesa.agregarCarta()"
        var mesaAct = this.copy(
            pilas = this.pilas.map { pila: PilaDeColor ->
                if (pila.color == colorPilaObjetivo) {
                    pila.agregarCarta(nuevaCarta)
                } else {
                    pila
                }
            },
        )
        return mesaAct
    }

    /**
     * @return Lista de cartas marcadas para Descartar
     */
    fun tomarCartasDePilasSegunEstado(): List<Carta> {
        val cartasParaDescarte: MutableList<Carta> = mutableListOf()

        this.pilas.map { pila: PilaDeColor ->
            cartasParaDescarte.addAll(
                when (pila.estado) {
                    PilaEstado.DEJAR_SOLO_ORGANO -> { pila.tomarCartasExcepto(CartaTipo.ORGANO) }
                    PilaEstado.DESCARTAR -> { pila.tomarCartasTodo() }
                    else -> { emptyList() }
                }
            )
        }
        return cartasParaDescarte
    }

    /**
     * @return Mesa con pila INMUNE
     * @return Mesa sin las cartas marcadas para descarte
     */
    fun accionarEstados(): Mesa {
        var a = this.copy(
            pilas = this.pilas.map{ pila: PilaDeColor ->
                when(pila.estado){
                    PilaEstado.INMUNIZAR -> { pila.inmunizar() }
                    PilaEstado.DEJAR_SOLO_ORGANO
                         -> { pila.quitarCartasExcepto(CartaTipo.ORGANO) }
                    PilaEstado.DESCARTAR -> { pila.vaciarPila() }
                    else -> { pila }
                }
            }
        )

        a = a.copy(
            turnosParaGanar = a.calcularTurnosParaGanar()
        )

        return a
    }

    /** Asumiendo que siempre habrá al menos 1 de cada color
     * @param color color de la pila que se pide
     * @return PilaDeColor con el color indicado
     */
    fun getPilaDeColor(color: CartaColor): PilaDeColor {
        return this.pilas.filter { pila: PilaDeColor ->
            pila.color == color
        }[0]
    }

    /**
     * @return el estado de la Pila con el [color] indicado
     */
    fun getEstadoPila(color: CartaColor): PilaEstado {
        return getPilaDeColor(color = color).estado
    }

    /** Calcula la menor cantidad de turno posibles para que el jugador gane la partida
     * basado unicamente en las cartas de la Mesa
     * @return cantidad de jugadas minimas
     */
    fun calcularTurnosParaGanar(): Int {
        // Lógica para calcular el nuevo valor
        var turnosParaGanar = 4
        this.pilas.forEach { pila: PilaDeColor ->
            turnosParaGanar += pila.sumaDePila()
        }
        return turnosParaGanar
    }
}