package com.example.notvirus.data.model

data class Mesa(
    val pilaAzul: List<Carta> = listOf(),
    val pilaRoja: List<Carta> = listOf(),
    val pilaVerde: List<Carta> = listOf(),
    val pilaAmarilla: List<Carta> = listOf(),
    val pilaMulticolor: List<Carta> = listOf(),
    val movementsParaGanar: Int = 4, // int = [ 0, 4 ]
) {
    // Métodos que devuelven nuevas instancias de Mesa

    fun agregarToAmarillo(nuevaCarta: Carta): Mesa {
        println("Amarillo añadido")
        return this.copy(pilaAmarilla = pilaAmarilla + nuevaCarta)
    }

    fun agregarToVerde(nuevaCarta: Carta): Mesa {
        println("Verde añadido")
        return this.copy(pilaVerde = pilaVerde + nuevaCarta)
    }

    fun agregarToRojo(nuevaCarta: Carta): Mesa {
        println("Rojo añadido")
        return this.copy(pilaRoja = pilaRoja + nuevaCarta)
    }

    fun agregarToAzul(nuevaCarta: Carta): Mesa {
        println("Azul añadido")
        return this.copy(pilaAzul = pilaAzul + nuevaCarta)
    }

    fun agregarToMulticolor(nuevaCarta: Carta): Mesa {
        println("Multicolor añadido")
        return this.copy(pilaMulticolor = pilaMulticolor + nuevaCarta)
    }

    fun quitarDeAmarillo(): Mesa {
        println("Multicolor quitado")
        return this.copy(pilaAmarilla = emptyList())
    }

    fun quitarDeVerde(): Mesa {
        println("Verde quitado")
        return this.copy(pilaVerde = emptyList())
    }

    fun quitarDeRojo(): Mesa {
        println("Rojo quitado")
        return this.copy(pilaRoja = emptyList())
    }

    fun quitarDeAzul(): Mesa {
        println("Azul quitado")
        return this.copy(pilaAzul = emptyList())
    }

    fun quitarDeMulticolor(): Mesa {
        println("Multicolor quitado")
        return this.copy(pilaMulticolor = emptyList())
    }
    
    fun calculateMovementsToWin(): Mesa {
        // Lógica para calcular el nuevo valor
        val movementsParaGanar = 4 // Coloca tu lógica aquí
        return this.copy(
            movementsParaGanar = movementsParaGanar
        )
    }
}