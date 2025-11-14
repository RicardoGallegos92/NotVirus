package com.example.notvirus.data.model

class Jugador(
    var nombre: String,
    var mano: Mano,
    var isActive: Boolean,
){
    fun takeCartas():Unit{
        // solicitar a la baraja
    }
    fun discardCartas():Unit{
        // enviar a la pila de descarte
    }
    fun playCarta():Unit{
        // mover una carta a la mesa
    }
}