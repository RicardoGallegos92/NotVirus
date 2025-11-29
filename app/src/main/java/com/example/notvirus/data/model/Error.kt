package com.example.notvirus.data.model

class CartasInsuficientes(message: String = "No existen suficientes cartas"): Exception(message)

class JugadaInvalida(message: String = "Jugada No Válida"): Exception(message)

