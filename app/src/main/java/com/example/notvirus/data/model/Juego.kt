package com.example.notvirus.data.model

class Juego(
    val jugadores:MutableList<Jugador>,
    val mesas:MutableList<Mesa>,
    val baraja: Baraja,
    val pilaDescarte: PilaDescarte,
) {
    fun winner():Unit{
        // mostrar ganador
    }

    fun dealCartas():Unit{
        // pasar cartas
    }

    fun passTurn():Unit{
        // cambiar jugador Activo
    }
}