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
    // Devuelve una copia actualizada de Mesa
    fun agregarCarta(nuevaCarta: Carta, colorPilaObjetivo: CartaColor): Mesa {
//        println("Mesa.agregarCarta()")
        var mesaAct = this.copy(
            pilas = this.pilas.map { pila: PilaDeColor ->
                if (pila.color == colorPilaObjetivo) {
                    pila.agregarCarta(nuevaCarta)
                } else {
                    pila
                }
            },
        )
        mesaAct = mesaAct.actualizar()

        return mesaAct
    }

    /**
     * @param color color de la pila que se debe inmunizar
     * @return Mesa con la pila indicada Inmunizada
     */
    fun inmunizarPila(color: CartaColor): Mesa {
        return this.copy(
            pilas = this.pilas.map { pila: PilaDeColor ->
                if (pila.color == color) {
                    pila.inmunizar()
                } else {
                    pila
                }
            }
        )
    }

    fun tomarCartasDePila(colorPila: CartaColor): List<Carta> {
        var pilaAct = this.getPilaDeColor(colorPila)

        var cartasTomadas = when (pilaAct.estado) {
            PilaEstado.DEJAR_SOLO_ORGANO -> {
                pilaAct.tomarCartasExcepto(CartaTipo.ORGANO)
            }

            PilaEstado.DESCARTAR -> {
                pilaAct.tomarCartasTodo()
            }

            else -> {
                emptyList()
            }
        }
        return cartasTomadas
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
     * @return Mesa sin la Lista de cartas marcadas para Descartar
     */
    fun quitarCartasDePilasSegunEstado(): Mesa {
        return this.copy(
            pilas = this.pilas.map { pila: PilaDeColor ->
                when (pila.estado) {
                    PilaEstado.DEJAR_SOLO_ORGANO -> {
                        pila.quitarCartasExcepto(CartaTipo.ORGANO)
                    }

                    PilaEstado.DESCARTAR -> {
                        pila.vaciarPila()
                    }

                    else -> {
                        pila
                    }
                }
            }
        )
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

    fun vaciarPila(color: CartaColor): Mesa {
        var mesaAct = this.copy(
            pilas = this.pilas.map { pila: PilaDeColor ->
                if (pila.color == color) {
                    pila.vaciarPila()
                } else {
                    pila
                }
            }
        )

        return mesaAct.actualizar()
    }

    /**
     * @return el estado de la Pila con el [color] indicado
     */
    fun getEstadoPila(color: CartaColor): PilaEstado {
        return getPilaDeColor(color = color).estado
    }

    fun actualizar(): Mesa {
        return this.copy(
            pilas = this.pilas.map { pila: PilaDeColor ->
                pila.actualizarEstado()
            },
            turnosParaGanar = this.calcularTurnosParaGanar()
        )
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