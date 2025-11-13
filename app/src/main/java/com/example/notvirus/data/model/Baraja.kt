package com.example.notvirus.data.model

import androidx.collection.MutableObjectList

class Baraja(
    var cartas: List<Carta> = listOf(
        Carta(tipo = CartaTipo.ORGANO, color = CartaColor.VERDE)
    ),
)