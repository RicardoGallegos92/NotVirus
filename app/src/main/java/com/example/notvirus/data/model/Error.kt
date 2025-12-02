package com.example.notvirus.data.model

class CartasInsuficientes(message: String = "No existen suficientes cartas"): Exception(message)

class JugadaInvalida(message: String = "Jugada No Válida"): Exception(message)

class CartaSinTipo(message: String = "Carta Jugada no posee un TIPO"): Exception(message)

class PilaConOrgano(message: String = "Pila ya tiene un ORGANO"): Exception(message)

class PilaSinOrgano(message: String = "Pila NO tiene un ORGANO"): Exception(message)