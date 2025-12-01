package com.example.notvirus.data.model

data class PilaDeColor(
    val color: CartaColor,
    val pila: List<Carta> = emptyList(),
    val estado: PilaEstado = PilaEstado.VACIO,
) {
    fun agregarCarta(carta: Carta): PilaDeColor {
        return this.copy(
            pila = ( this.pila + carta )
        )
    }

    /**
     * @return Lista de cartas que difieren del Tipo indicado
     */
    fun tomarCartasExcepto(tipo: CartaTipo): List<Carta> {
        return pila.filter { it.tipo != tipo }
    }

    /**
     * @return Pila sin las cartas del Tipo indicado
     */
    fun quitarCartasExcepto(tipo: CartaTipo): PilaDeColor {
        return this.copy(
            pila = this.pila.filter { it.tipo != tipo }
        )
    }

    fun tomarCartasTodo(): List<Carta> {
        return this.pila
    }

    /**
     * @return una copia de la [PilaDeColor] :[pila] -> empltyList()
     */
    fun vaciarPila(): PilaDeColor {
        return this.copy(
            pila = emptyList()
        )
    }

    /** actualiza el estado de la Pila según las cartas que contiene
     * @return PilaDeColor con el estado correspondiente
     */
    fun actualizarEstado(): PilaDeColor {
        if ( this.estado == PilaEstado.INMUNE ){
            // si ya es INMUNE no es necesario hacer algo
            return this
        }

//        println("Pila.actualizarEstado()")
        var listaCartas = this.pila

        val organos =listaCartas.filter {
            it.tipo == CartaTipo.ORGANO
        }

        val virus = listaCartas.filter {
            it.tipo == CartaTipo.VIRUS
        }
        val medicinas = listaCartas.filter {
            it.tipo == CartaTipo.MEDICINA
        }

        return this.copy(
            estado = when {
                (virus.size == 2) -> { PilaEstado.DESCARTAR }
                (medicinas.size == 2) -> { PilaEstado.INMUNIZAR }
                (medicinas.size == 1
                        && virus.size == 1) -> { PilaEstado.DEJAR_SOLO_ORGANO }
                else -> {
                    if (organos.size == 1){
                        PilaEstado.CON_ORGANO
                    }else{
                        PilaEstado.VACIO
                    }
                }
            }
        )
    }

    /** Cambia el atributo 'esInmune' de todas las [Carta] en la [pila].
     * Cambia [estado] a INMUNE
     * @return PilaDeColor con [estado] = INMUNE
     */
    fun inmunizar(): PilaDeColor {
        println("PilaDeColor: ${this.color} ha sido Inmunizada")
        return this.copy(
            pila = pila.map { carta: Carta ->
                carta.copy(
                    esInmune = true,
                )
            }
            ,
            estado = PilaEstado.INMUNE
        )
    }

    fun sumaDePila():Int{
        var conteo:Int = 0

        this.pila.forEach { carta:Carta ->
            conteo += when (carta.tipo) {
                CartaTipo.ORGANO -> { -1 }
                CartaTipo.VIRUS -> { 1 }
// CartaTipo.MEDICINA // -> NO mueve el contador
                else -> { 0 }
            }
        }
        return conteo
    }

}