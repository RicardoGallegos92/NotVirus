package com.example.notvirus.data.model

import java.util.UUID

data class Carta(
    var tipo: CartaTipo,
    val color: CartaColor,
    var cartaImagen: CartaImagen,
    var seleccionada: Boolean = false,
    val id: String = UUID.randomUUID().toString(),
){

    fun rotateCarta():Unit{
        // animacion
    }
}