package com.example.notvirus.data.model

class Mano(
    var cartas: MutableList<Carta>, // máximo 3 elementos
) {
    fun addCarta(nuevasCartas: MutableList<Carta>): Unit {
        cartas.addAll(nuevasCartas);
    }

    fun removeSelectedCartas(): Unit {
        cartas.removeIf {
            it.seleccionada
        }
    }

    fun takeSelectedCarta():MutableList<Carta>{
        var seleccionadas = cartas.filter {
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