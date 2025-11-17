package com.example.notvirus.data.model

class Mesa(
    var pilaAzul: MutableList<Carta>,
    var pilaRoja: MutableList<Carta>,
    var pilaVerde: MutableList<Carta>,
    var pilaAmarilla: MutableList<Carta>,
    var pilaMulticolor: MutableList<Carta>,
    var movementsToWin: Int,
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