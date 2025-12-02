package com.example.notvirus.data.model

data class PilaDeColor(
    val color: CartaColor,
    val cartas: List<Carta> = emptyList(),
    val estado: PilaEstado = PilaEstado.VACIO,
) {
    fun agregarCarta(carta: Carta): PilaDeColor {
        val a = this.copy(
            cartas = ( this.cartas + carta )
        )
        return a.actualizarEstado()
    }

    /**
     * @return Lista de cartas que difieren del Tipo indicado
     */
    fun tomarCartasExcepto(tipo: CartaTipo): List<Carta> {
        return this.cartas.filter { it.tipo != tipo }
    }

    /**
     * @return Pila solo con las cartas del Tipo indicado
     */
    fun quitarCartasExcepto(tipo: CartaTipo): PilaDeColor {
        var a = this.copy(
            cartas = this.cartas.filter { it.tipo == tipo }
        )
        a = a.actualizarEstado()

        return a
    }

    /**
     * @return Lista de Cartas
     */
    fun tomarCartasTodo(): List<Carta> {
        return this.cartas
    }

    /**
     * @return una copia de la [PilaDeColor] :[cartas] -> empltyList()
     */
    fun vaciarPila(): PilaDeColor {
        val a =this.copy(
            cartas = emptyList()
        )
        return a.actualizarEstado()
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
        val listaCartas = this.cartas

        val organos = listaCartas.filter { it.tipo == CartaTipo.ORGANO }
        val virus = listaCartas.filter { it.tipo == CartaTipo.VIRUS }
        val medicinas = listaCartas.filter { it.tipo == CartaTipo.MEDICINA }

         val a = this.copy(
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
        /*
        if (a.estado != PilaEstado.VACIO){
            println("Pila-${a.color} -> estado: ${a.estado}")
        }
        */
        return a
    }

    /** Cambia el atributo 'esInmune' de todas las [Carta] en la [cartas].
     * Cambia [estado] a INMUNE
     * @return PilaDeColor con [estado] = INMUNE
     */
    fun inmunizar(): PilaDeColor {
        println("PilaDeColor: ${this.color} ha sido Inmunizada")
        return this.copy(
            cartas = this.cartas.map { carta: Carta ->
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

        this.cartas.forEach { carta:Carta ->
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