package com.example.notvirus.data.model

class Mesa(
    var pilaAzul: MutableList<Carta> = mutableListOf(),
    var pilaRoja: MutableList<Carta> = mutableListOf(),
    var pilaVerde: MutableList<Carta> = mutableListOf(),
    var pilaAmarilla: MutableList<Carta> = mutableListOf(),
    var pilaMulticolor: MutableList<Carta> = mutableListOf(),
    var movementsToWin: Int = 4, // int = [ 0, 4 ]
) {
    // metodos
    fun calculateMovementsToWin(): Unit {
        // calcular minimo de turnos para ganar
    }

    fun addAmarillo(nuevaCarta:Carta): Unit {
        pilaAmarilla.add(nuevaCarta)
    }

    fun addVerde(nuevaCarta:Carta): Unit {
        pilaVerde.add(nuevaCarta)
    }

    fun addRojo(nuevaCarta:Carta): Unit {
        pilaRoja.add(nuevaCarta)
    }

    fun addAzul(nuevaCarta:Carta): Unit {
        pilaAzul.add(nuevaCarta)
    }

    fun addMulticolor(nuevaCarta:Carta): Unit {
        pilaMulticolor.add(nuevaCarta)
    }

    fun flushAmarillo(): Unit {
        pilaAmarilla.clear()
    }

    fun flushVerde(): Unit {
        pilaVerde.clear()
    }

    fun flushRojo(): Unit {
        pilaRoja.clear()
    }

    fun flushAzul(): Unit {
        pilaAzul.clear()
    }

    fun flushMulticolor(): Unit {
        pilaMulticolor.clear()
    }
}