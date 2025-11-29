package com.example.notvirus.data.model

import kotlin.collections.toMutableList

data class Baraja(
    val pila: List<Carta> = crearMazoInicial(), // Cambiado a List y valor por defecto
) {
    companion object {
        fun crearMazoInicial(): List<Carta> {
            val cartas = mutableListOf<Carta>()

            // 21 cartas de órganos
            repeat(5) {
                cartas.add(
                    Carta(
                        CartaTipo.ORGANO, CartaColor.ROJO, CartaImagen.CORAZON
                    )
                )
            }
            repeat(5) {
                cartas.add(
                    Carta(
                        CartaTipo.ORGANO, CartaColor.AZUL, CartaImagen.CEREBRO
                    )
                )
            }
            repeat(5) {
                cartas.add(
                    Carta(
                        CartaTipo.ORGANO, CartaColor.AMARILLO, CartaImagen.HUESO
                    )
                )
            }
            repeat(5) {
                cartas.add(
                    Carta(
                        CartaTipo.ORGANO, CartaColor.VERDE, CartaImagen.ESTOMAGO
                    )
                )
            }
            cartas.add(
                Carta(
                    CartaTipo.ORGANO, CartaColor.MULTICOLOR, CartaImagen.CUERPO
                )
            )

            // 17 cartas de virus
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.VIRUS, CartaColor.ROJO, CartaImagen.VIRUS_ROJO
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.VIRUS, CartaColor.VERDE, CartaImagen.VIRUS_VERDE
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.VIRUS, CartaColor.AZUL, CartaImagen.VIRUS_AZUL
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.VIRUS, CartaColor.AMARILLO, CartaImagen.VIRUS_AMARILLO
                    )
                )
            }
            cartas.add(
                Carta(
                    CartaTipo.VIRUS, CartaColor.MULTICOLOR, CartaImagen.VIRUS_MULTICOLOR
                )
            )

            // 20 cartas de medicinas
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.MEDICINA, CartaColor.ROJO, CartaImagen.MEDICINA_ROJO
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.MEDICINA, CartaColor.VERDE, CartaImagen.MEDICINA_VERDE
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.MEDICINA, CartaColor.AZUL, CartaImagen.MEDICINA_AZUL
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.MEDICINA, CartaColor.AMARILLO, CartaImagen.MEDICINA_AMARILLO
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.MEDICINA, CartaColor.MULTICOLOR, CartaImagen.MEDICINA_MULTICOLOR
                    )
                )
            }

            // 10 cartas de tratamientos
            repeat(2) {
                cartas.add(
                    Carta(
                        CartaTipo.TRATAMIENTO, CartaColor.TRATAMIENTO, CartaImagen.TRATAMIENTO_CONTAGIO
                    )
                )
            }
            repeat(3) {
                cartas.add(
                    Carta(
                        CartaTipo.TRATAMIENTO,
                        CartaColor.TRATAMIENTO,
                        CartaImagen.TRATAMIENTO_ROBO_ORGANO
                    )
                )
            }
            repeat(3) {
                cartas.add(
                    Carta(
                        CartaTipo.TRATAMIENTO,
                        CartaColor.TRATAMIENTO,
                        CartaImagen.TRATAMIENTO_TRANSPLANTE
                    )
                )
            }
            repeat(1) {
                cartas.add(
                    Carta(
                        CartaTipo.TRATAMIENTO,
                        CartaColor.TRATAMIENTO,
                        CartaImagen.TRATAMIENTO_ERROR_MEDICO
                    )
                )
            }
            repeat(1) {
                cartas.add(
                    Carta(
                        CartaTipo.TRATAMIENTO,
                        CartaColor.TRATAMIENTO,
                        CartaImagen.TRATAMIENTO_GUANTE_LATEX
                    )
                )
            }

            repeat(10) { cartas.shuffle() }
            return cartas
        }
    }

    // Devuelve cartas solicitadas y la Baraja sin esas cartas
    fun pedirCartas(cantCartas: Int): Pair<List<Carta>, Baraja> {
        val cartasPedidas = pila.take(cantCartas)
        val nuevoMazo = pila.drop(cantCartas) // Nuevo pila sin las cartas tomadas
        return Pair(
            cartasPedidas,
            this.copy(
                pila = nuevoMazo
            ),
        )
    }

    /**
     * @return Devuelve una nueva Baraja con las cartas agregadas
     */
    fun agregarCartas(cartasParaAgregar: List<Carta>): Baraja {
        var nuevoMazo = (pila + cartasParaAgregar)
        return this.copy(
            pila = nuevoMazo
        )
    }

    /**
     * @return Devuelve una baraja revuelta 10 veces
     */
    fun revolver(): Baraja {
        var cartas = this.pila.toMutableList()
        repeat(10) { cartas.shuffle() }
        return this.copy(
            pila = cartas
        )
    }
}