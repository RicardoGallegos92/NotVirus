package com.example.notvirus.data.model

class Baraja(
    val mazo: MutableList<Carta> = mutableListOf(),
) {
    init {
        // 21 cartas de órganos
        repeat(5) { mazo.add(Carta(CartaTipo.ORGANO, CartaColor.ROJO,imagen =  CartaImagen.CORAZON, icono = CartaIcono.CORAZON)) }
        repeat(5) { mazo.add(Carta(tipo = CartaTipo.ORGANO, color = CartaColor.AZUL, imagen = CartaImagen.CEREBRO, icono = CartaIcono.CEREBRO)) }
        repeat(5) { mazo.add(Carta(tipo = CartaTipo.ORGANO, color = CartaColor.AMARILLO, imagen = CartaImagen.HUESO, icono = CartaIcono.HUESO)) }
        repeat(5) { mazo.add(Carta(tipo = CartaTipo.ORGANO, color = CartaColor.VERDE,imagen =  CartaImagen.ESTOMAGO, icono = CartaIcono.ESTOMAGO)) }
        mazo.add(Carta(tipo = CartaTipo.ORGANO, color = CartaColor.MULTICOLOR, imagen = CartaImagen.CUERPO, icono = CartaIcono.CUERPO)) // 1 comodín de órgano (CUERPO)

        // 17 cartas de virus
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.VIRUS, color = CartaColor.ROJO,imagen =  CartaImagen.VIRUS_ROJO, icono = CartaIcono.VIRUS)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.VIRUS, color = CartaColor.VERDE, imagen = CartaImagen.VIRUS_VERDE, icono = CartaIcono.VIRUS)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.VIRUS, color = CartaColor.AZUL,imagen =  CartaImagen.VIRUS_AZUL, icono = CartaIcono.VIRUS)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.VIRUS, color = CartaColor.AMARILLO,imagen =  CartaImagen.VIRUS_AMARILLO, icono = CartaIcono.VIRUS)) }
        mazo.add(Carta(tipo = CartaTipo.VIRUS, color = CartaColor.MULTICOLOR, imagen = CartaImagen.VIRUS_MULTICOLOR,icono = CartaIcono.VIRUS)) // 1 comodín

        // 20 cartas de medicinas
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.MEDICINA, color = CartaColor.ROJO, imagen = CartaImagen.MEDICINA_ROJO, icono = CartaIcono.MEDICINA)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.MEDICINA, color = CartaColor.VERDE, imagen = CartaImagen.MEDICINA_VERDE, icono = CartaIcono.MEDICINA)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.MEDICINA, color = CartaColor.AZUL, imagen = CartaImagen.MEDICINA_AZUL, icono = CartaIcono.MEDICINA)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.MEDICINA, color = CartaColor.AMARILLO, imagen = CartaImagen.MEDICINA_AMARILLO, icono = CartaIcono.MEDICINA)) }
        repeat(4) { mazo.add(Carta(tipo = CartaTipo.MEDICINA, color = CartaColor.MULTICOLOR, imagen = CartaImagen.MEDICINA_MULTICOLOR, icono = CartaIcono.MEDICINA)) }

        // 10 cartas de tratamientos
        repeat(2) { mazo.add(Carta(tipo = CartaTipo.TRATAMIENTO, color = CartaColor.BLANCO, imagen = CartaImagen.TRATAMIENTO_CONTAGIO , icono = CartaIcono.TRATAMIENTO)) }
        repeat(3) { mazo.add(Carta(tipo = CartaTipo.TRATAMIENTO, color = CartaColor.BLANCO, imagen = CartaImagen.TRATAMIENTO_ROBO_ORGANO , icono = CartaIcono.TRATAMIENTO)) }
        repeat(3) { mazo.add(Carta(tipo = CartaTipo.TRATAMIENTO, color = CartaColor.BLANCO, imagen = CartaImagen.TRATAMIENTO_TRANSPLANTE , icono = CartaIcono.TRATAMIENTO)) }
        repeat(1) { mazo.add(Carta(tipo = CartaTipo.TRATAMIENTO, color = CartaColor.BLANCO, imagen = CartaImagen.TRATAMIENTO_ERROR_MEDICO , icono = CartaIcono.TRATAMIENTO)) }
        repeat(1) { mazo.add(Carta(tipo = CartaTipo.TRATAMIENTO, color = CartaColor.BLANCO, imagen = CartaImagen.TRATAMIENTO_GUANTE_LATEX , icono = CartaIcono.TRATAMIENTO)) }

        repeat(10){ shuffle() }

    }
    // barajar
    private fun shuffle(): Unit {
        // random sort
        mazo.shuffle()
    }

    // @params n => cantidad de cartas pedidas
    fun takeCartas(n: Int): MutableList<Carta> {
        val cartas = mazo.subList(0, n).toMutableList()
        mazo.removeAll(cartas)
        return cartas
    }

    fun reassemble(descarte: MutableList<Carta>){
        mazo.addAll(descarte)
        shuffle()
    }
}