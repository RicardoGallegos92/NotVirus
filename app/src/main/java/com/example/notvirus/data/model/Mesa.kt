package com.example.notvirus.data.model

class Mesa(
    var zonaCartas: MutableList <MutableList <Carta>>,// matriz de 5 x 3
    var movementsToWin: Int,
) {
    // metodos
    fun calculateMovementsToWin(): Unit {
        // calcular minimo de turnos para ganar
    }
}