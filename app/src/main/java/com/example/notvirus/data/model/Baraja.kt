package com.example.notvirus.data.model

class Baraja(
    val mazo: MutableList<Carta> = mutableListOf(),
) {
    init {
        // 21 cartas de órganos
        repeat(5) { mazo.add(Carta(CartaTipo.ORGANO, CartaColor.ROJO,cartaImagen =  CartaImagen.CORAZON)) }
        repeat(5) { mazo.add(Carta(tipo = CartaTipo.ORGANO, color = CartaColor.AZUL, cartaImagen = CartaImagen.CEREBRO)) }
        repeat(5) { mazo.add(Carta(tipo = CartaTipo.ORGANO, color = CartaColor.AMARILLO, cartaImagen = CartaImagen.HUESO)) }
        repeat(5) { mazo.add(Carta(tipo = CartaTipo.ORGANO, color = CartaColor.VERDE,cartaImagen =  CartaImagen.ESTOMAGO)) }
        mazo.add(Carta(tipo = CartaTipo.ORGANO, color = CartaColor.MULTICOLOR, cartaImagen = CartaImagen.CUERPO)) // 1 comodín de órgano (CUERPO)

        // 17 cartas de virus
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.VIRUS, color = CartaColor.ROJO,cartaImagen =  CartaImagen.VIRUS)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.VIRUS, color = CartaColor.VERDE, cartaImagen = CartaImagen.VIRUS)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.VIRUS, color = CartaColor.AZUL,cartaImagen =  CartaImagen.VIRUS)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.VIRUS, color = CartaColor.AMARILLO,cartaImagen =  CartaImagen.VIRUS)) }
        mazo.add(Carta(tipo = CartaTipo.VIRUS, color = CartaColor.MULTICOLOR, cartaImagen = CartaImagen.VIRUS)) // 1 comodín

        // 20 cartas de medicinas
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.MEDICINA, color = CartaColor.ROJO, cartaImagen = CartaImagen.MEDICINA)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.MEDICINA, color = CartaColor.VERDE, cartaImagen = CartaImagen.MEDICINA)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.MEDICINA, color = CartaColor.AZUL, cartaImagen = CartaImagen.MEDICINA)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.MEDICINA, color = CartaColor.AMARILLO, cartaImagen = CartaImagen.MEDICINA)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.MEDICINA, color = CartaColor.MULTICOLOR, cartaImagen = CartaImagen.MEDICINA)) }

        // 10 cartas de tratamientos
        repeat(10) { mazo.add(Carta(tipo = CartaTipo.TRATAMIENTO, color = CartaColor.BLANCO, cartaImagen = CartaImagen.TRATAMIENTO)) }

        repeat(10){ shuffle() }

    }
    // barajar
    private fun shuffle(): Unit {
        // random sort
        mazo.shuffle()
    }

    // @params n => cantidad de cartas pedidas
    fun takeCartas(n: Int): MutableList <Carta> {
        val cartas = mazo.subList(0, n - 1)
        mazo.removeAll(cartas)
        return cartas
    }

    fun reassemble(descarte: MutableList<Carta>){
        mazo.addAll(descarte)
        shuffle()
    }
}