package com.example.notvirus.data.model

data class Mesa(
    val pilaAzul: List<Carta> = listOf(),
    val pilaRoja: List<Carta> = listOf(),
    val pilaVerde: List<Carta> = listOf(),
    val pilaAmarilla: List<Carta> = listOf(),
    val pilaMulticolor: List<Carta> = listOf(),
    val movementsToWin: Int = 4, // int = [ 0, 4 ]
) {
    // Métodos que devuelven nuevas instancias de Mesa

    fun addAmarillo(nuevaCarta: Carta): Mesa {
        return this.copy(pilaAmarilla = this.pilaAmarilla + nuevaCarta)
    }

    fun addVerde(nuevaCarta: Carta): Mesa {
        return this.copy(pilaVerde = this.pilaVerde + nuevaCarta)
    }

    fun addRojo(nuevaCarta: Carta): Mesa {
        return this.copy(pilaRoja = this.pilaRoja + nuevaCarta)
    }

    fun addAzul(nuevaCarta: Carta): Mesa {
        return this.copy(pilaAzul = this.pilaAzul + nuevaCarta)
    }

    fun addMulticolor(nuevaCarta: Carta): Mesa {
        return this.copy(pilaMulticolor = this.pilaMulticolor + nuevaCarta)
    }

    fun flushAmarillo(): Mesa {
        return this.copy(pilaAmarilla = emptyList())
    }

    fun flushVerde(): Mesa {
        return this.copy(pilaVerde = emptyList())
    }

    fun flushRojo(): Mesa {
        return this.copy(pilaRoja = emptyList())
    }

    fun flushAzul(): Mesa {
        return this.copy(pilaAzul = emptyList())
    }

    fun flushMulticolor(): Mesa {
        return this.copy(pilaMulticolor = emptyList())
    }

    // Si necesitas recalcular movementsToWin, puedes tener un método que devuelva una nueva Mesa
    fun calculateMovementsToWin(): Mesa {
        // Lógica para calcular el nuevo valor
        val nuevoMovimientos = 4 // Coloca tu lógica aquí
        return this.copy(movementsToWin = nuevoMovimientos)
    }
}