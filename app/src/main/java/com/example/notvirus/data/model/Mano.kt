package com.example.notvirus.data.model

class Mano(
    val cartas: MutableList<Carta> = mutableListOf(), // máximo 3 elementos
) {
    fun addCartas(nuevasCartas: MutableList<Carta>) {
        cartas.addAll(nuevasCartas)
    }

    fun removeSelectedCartas() {
        cartas.removeIf {
            it.seleccionada
        }
    }

    fun takeSelectedCarta():MutableList<Carta>{
        val seleccionadas = cartas.filter {
            it.seleccionada
        } as MutableList<Carta>

        return seleccionadas
    }

    fun selectCarta(cartaElegida: Carta) {
        val index = cartas.indexOf(cartaElegida)
        cartas[index].seleccionada = true
    }

    fun unSelectCarta(cartaElegida: Carta) {
        val index = cartas.indexOf(cartaElegida)
        cartas[index].seleccionada = false
    }
}