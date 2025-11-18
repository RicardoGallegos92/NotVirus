package com.example.notvirus.data.model

data class Jugador(
    val nombre: String = "Player X", // nombre de Usuario
    val isActive: Boolean = false,
    val mano: Mano = Mano(),
    val mesa: Mesa = Mesa(),
) {
    // Método para "tomar cartas" y devolver un nuevo Jugador con la mano actualizada
    fun takeCartas(nuevasCartas: List<Carta>): Jugador {
        println("Jugador.takeCartas()")
        val nuevaMano = mano.addCartas(nuevasCartas = nuevasCartas)
        return this.copy(mano = nuevaMano)
    }

    // Método para "descartar cartas" y devolver un nuevo Jugador con la mano actualizada
    fun discardCartas(): Pair<Jugador, List<Carta>> {
        println("Jugador.discardCartas()")
        // jugador separa las cartas a descartar
        val cartasDescartadas = mano.takeSelectedCarta()
        // jugador mantiene en mano las NO seleccionadas
        val nuevaMano = mano.removeSelectedCartas()
        val nuevoJugador = this.copy(mano = nuevaMano)
        // jugador pasa las cartas para descartar
        return Pair(nuevoJugador, cartasDescartadas)
    }

    fun actualizarMano(index: Int): Jugador {
        val manoAct = mano.selectCarta(mano.cartas[index])
        return this.copy(
            mano = manoAct
        )
    }

    fun jugarCarta(): Jugador {
        // qué carta se pretende jugar
        val cartaSeleccionada = mano.takeSelectedCarta()
        val carta = cartaSeleccionada[0]
        // ¿puede jugarse? - depende del tipo
        var mesaAct: Mesa = this.mesa
        var manoAct: Mano = this.mano
        when (carta.tipo) {
            CartaTipo.ORGANO ->
                if (!existeOrgano(color = carta.color)) {
                    mesaAct = addOrgano(cartaJugada = carta)
                    manoAct = mano.removeSelectedCartas()
                } else {
                    // lanzar mensaje (snackBar)
                }

            CartaTipo.VIRUS -> {}
            CartaTipo.MEDICINA -> {}
            CartaTipo.TRATAMIENTO -> {}
            else -> {}
        }
        return this.copy(
            mesa = mesaAct,
            mano = manoAct
        )
    }

    private fun addOrgano(cartaJugada: Carta): Mesa {
        val nuevaMesa = when (cartaJugada.color) {
            CartaColor.AMARILLO ->
                if (!existeOrgano(CartaColor.AMARILLO)) {
                    mesa.addAmarillo(cartaJugada)
                } else mesa

            CartaColor.VERDE ->
                if (!existeOrgano(CartaColor.VERDE)) {
                    mesa.addVerde(cartaJugada)
                } else mesa

            CartaColor.ROJO ->
                if (!existeOrgano(CartaColor.ROJO)) {
                    mesa.addRojo(cartaJugada)
                } else mesa

            CartaColor.AZUL ->
                if (!existeOrgano(CartaColor.AZUL)) {
                    mesa.addAzul(cartaJugada)
                } else mesa

            CartaColor.MULTICOLOR ->
                if (!existeOrgano(CartaColor.MULTICOLOR)) {
                    mesa.addMulticolor(cartaJugada)
                } else mesa

            else -> mesa
        }
        return nuevaMesa
    }

    // verifica si la pila ya está ocupada por un órgano
    private fun existeOrgano(color: CartaColor): Boolean {
        return when (color) {
            CartaColor.ROJO -> mesa.pilaRoja.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.AZUL -> mesa.pilaAzul.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.AMARILLO -> mesa.pilaAmarilla.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.VERDE -> mesa.pilaVerde.any { it.tipo == CartaTipo.ORGANO }
            CartaColor.MULTICOLOR -> mesa.pilaMulticolor.any { it.tipo == CartaTipo.ORGANO }
            else -> false
        }
    }

    private fun addMedicina(cartaJugada: Carta): Mesa {
        val nuevaMesa = when (cartaJugada.color) {
            CartaColor.AMARILLO ->
                if (existeOrgano(CartaColor.AMARILLO)) {
                    mesa.addAmarillo(cartaJugada)
                } else mesa

            CartaColor.VERDE ->
                if (existeOrgano(CartaColor.VERDE)) {
                    mesa.addVerde(cartaJugada)
                } else mesa

            CartaColor.ROJO ->
                if (existeOrgano(CartaColor.ROJO)) {
                    mesa.addRojo(cartaJugada)
                } else mesa

            CartaColor.AZUL ->
                if (existeOrgano(CartaColor.AZUL)) {
                    mesa.addAzul(cartaJugada)
                } else mesa

            CartaColor.MULTICOLOR ->
                if (existeOrgano(CartaColor.MULTICOLOR)) {
                    mesa.addMulticolor(cartaJugada)
                } else mesa

            else -> mesa
        }
        return nuevaMesa
    }

    private fun addVirus(cartaJugada: Carta): Mesa {
        val nuevaMesa = when (cartaJugada.color) {
            CartaColor.AMARILLO ->
                if (!existeOrgano(CartaColor.AMARILLO)) {
                    mesa.addAmarillo(cartaJugada)
                } else mesa

            CartaColor.VERDE ->
                if (!existeOrgano(CartaColor.VERDE)) {
                    mesa.addVerde(cartaJugada)
                } else mesa

            CartaColor.ROJO ->
                if (!existeOrgano(CartaColor.ROJO)) {
                    mesa.addRojo(cartaJugada)
                } else mesa

            CartaColor.AZUL ->
                if (!existeOrgano(CartaColor.AZUL)) {
                    mesa.addAzul(cartaJugada)
                } else mesa

            CartaColor.MULTICOLOR ->
                if (!existeOrgano(CartaColor.MULTICOLOR)) {
                    mesa.addMulticolor(cartaJugada)
                } else mesa

            else -> mesa
        }
        return nuevaMesa
    }

    private fun playTratamiento(cartaJugada: Carta): Jugador {
        // tratamiento tiene efecto particular y se va directo a la pila de descarte
        return this.copy(

        )
    }
}