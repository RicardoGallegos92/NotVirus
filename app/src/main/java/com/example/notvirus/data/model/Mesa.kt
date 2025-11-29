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
        println("Mesa.agregarCarta()")
        var mesaAct = this.copy(
            pilas = this.pilas.map { pila: PilaDeColor ->
                if (pila.color == colorPilaObjetivo) {
                    pila.agregarCarta(nuevaCarta)
                } else {
                    pila
                }
            },
        )
        mesaAct = mesaAct.actualizarEstadoPilas()

        return mesaAct
    }

    /**
     * @param color color de la pila que se debe inmunizar
     * @return Mesa con la pila indicada Inmunizada
     */
    fun inmunizarPila( color: CartaColor ): Mesa {
        return this.copy(
            pilas = this.pilas.map{ pila: PilaDeColor ->
                if (pila.color == color){
                    pila.inmunizar()
                }else{
                    pila
                }
            }
        )
    }

    fun tomarCartasDePila(colorPila: CartaColor): List<Carta> {
        var pilaAct = this.getPilaDeColor(colorPila)
        var cartasTomadas = when(pilaAct.estado){
            PilaEstado.DEJAR_SOLO_ORGANO -> { pilaAct.tomarCartasExcepto(CartaTipo.ORGANO) }
            PilaEstado.DESCARTAR -> { pilaAct.tomarCartasTodo() }
            else -> { emptyList() }
        }
        return cartasTomadas
    }

    fun getPilaDeColor(color: CartaColor): PilaDeColor{
        return this.pilas.filter { pila: PilaDeColor -> pila.color == color }[0]
    }

    fun vaciarPila(colorPila: CartaColor): Mesa {
        var mesaAct = this.copy(
            pilas = this.pilas.map { pila: PilaDeColor ->
                if (pila.color == colorPila) {
                    pila.vaciarPila()
                } else {
                    pila
                }
            }
        )

        return mesaAct.actualizarEstadoPilas()
    }

    fun actualizarEstadoPilas():Mesa{
        return this.copy(
            pilas = this.pilas.map { pila: PilaDeColor ->
                pila.actualizarEstado()
            }
            ,
            turnosParaGanar = this.calcularTurnosParaGanar()
        )
    }

    fun calcularTurnosParaGanar(): Int {
        // Lógica para calcular el nuevo valor
        var turnosParaGanar = 4
        this.pilas.forEach { (_, pila, _) ->
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