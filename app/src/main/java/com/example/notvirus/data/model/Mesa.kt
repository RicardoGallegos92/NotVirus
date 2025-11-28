package com.example.notvirus.data.model

data class Mesa(
    val pilas: Map<CartaColor, MutableList<Carta>> = mapOf(
        CartaColor.AMARILLO to mutableListOf(),
        CartaColor.AZUL to mutableListOf(),
        CartaColor.ROJO to mutableListOf(),
        CartaColor.VERDE to mutableListOf(),
        CartaColor.MULTICOLOR to mutableListOf(),
    ),
    val pilasB: List<PilaDeCartas> = listOf(
        PilaDeCartas(CartaColor.AMARILLO),
        PilaDeCartas(CartaColor.AZUL),
        PilaDeCartas(CartaColor.ROJO),
        PilaDeCartas(CartaColor.VERDE),
        PilaDeCartas(CartaColor.MULTICOLOR),
    ),
    val turnosParaGanar: Int = 4, // int = [ 0, 4 ]
) {
    // Devuelve nueva instancia de Mesa
    fun agregarToPila(nuevaCarta: Carta, colorPila: CartaColor? = null): Mesa {
        val pilasCopia = pilas.toMutableMap()
        // pregunta enfocada en el uso de MULTICOLOR
        // me indican una pila donde colocar la carta
        val color = colorPila ?: nuevaCarta.color

        if(pilasCopia[color]?.size == 3){
            return this.copy()
        }

        pilasCopia[color]?.add(nuevaCarta)
        // tiene la pila 3 cartas
        if(pilasCopia[color]?.size == 3){
            pilasCopia[color] = revisarPila(pilasCopia[color]!!)
        }

        return this.copy(
            pilas = pilasCopia,
            turnosParaGanar = calcularTurnosParaGanar(pilasCopia = pilasCopia)
        )
    }

    fun revisarPila(pila: MutableList<Carta>):MutableList<Carta>{
        var pilaRevisada : MutableList<Carta> = mutableListOf()
        val virus = pila.filter{
            it.tipo == CartaTipo.VIRUS
        }
        val medicinas = pila.filter{
            it.tipo == CartaTipo.MEDICINA
        }

        if(virus.size == 2){
            pilaRevisada = pila.filter{
                it.tipo == CartaTipo.ORGANO
            }.toMutableList()
            // aquí hay que ver porque los virus se tienen que ir a descarte
        }else if(medicinas.size == 2){
            pilaRevisada = inmunizarPila(pila)
        }
        return pilaRevisada
    }

    fun inmunizarPila(pila: MutableList<Carta>):MutableList<Carta>{
        val cartaInmune = pila.last().copy(
            esInmune = true
        )
        val pilaInmunizada = pila.map{ carta:Carta ->
            if( carta.id == cartaInmune.id){
                cartaInmune
            }else{
                carta
            }
        }.toMutableList()
        return pilaInmunizada
    }

    fun quitarDePila(cartasParaQuitar: List<Carta>, colorPila: CartaColor): Mesa {
        val pilasCopia = pilas.toMutableMap()
        pilasCopia[colorPila]!!.removeAll(cartasParaQuitar)
        return this.copy(
            pilas = pilasCopia,
            turnosParaGanar = calcularTurnosParaGanar(pilasCopia = pilasCopia)
        )
    }

    fun vaciarPila(cartasParaQuitar: List<Carta>, colorPila: CartaColor): Mesa {
        val pilasCopia = pilas.toMutableMap()
        pilasCopia[colorPila]!!.removeAll(cartasParaQuitar)
        return this.copy(
            pilas = pilasCopia,
            turnosParaGanar = calcularTurnosParaGanar(pilasCopia = pilasCopia)
        )
    }
    fun calcularTurnosParaGanar(pilasCopia:Map<CartaColor, MutableList<Carta>>): Int {
        // Lógica para calcular el nuevo valor
        var turnosParaGanar = 4
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