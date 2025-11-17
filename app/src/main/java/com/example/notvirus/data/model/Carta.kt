package com.example.notvirus.data.model

import java.util.UUID

data class Carta(
    val tipo: CartaTipo,
    val color: CartaColor,
    val icono: CartaIcono,
    val imagen: CartaImagen,
    var seleccionada: Boolean = false,
    val id: String = UUID.randomUUID().toString(),
){

    fun rotateCarta():Unit{
        // animacion
    }
}