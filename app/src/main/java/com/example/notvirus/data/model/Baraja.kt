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
                        CartaTipo.ORGANO,
                        CartaColor.ROJO,
                        CartaIcono.CORAZON,
                        CartaImagen.CORAZON
                    )
                )
            }
            repeat(5) {
                cartas.add(
                    Carta(
                        CartaTipo.ORGANO,
                        CartaColor.AZUL,
                        CartaIcono.CEREBRO,
                        CartaImagen.CEREBRO
                    )
                )
            }
            repeat(5) {
                cartas.add(
                    Carta(
                        CartaTipo.ORGANO,
                        CartaColor.AMARILLO,
                        CartaIcono.HUESO,
                        CartaImagen.HUESO
                    )
                )
            }
            repeat(5) {
                cartas.add(
                    Carta(
                        CartaTipo.ORGANO,
                        CartaColor.VERDE,
                        CartaIcono.ESTOMAGO,
                        CartaImagen.ESTOMAGO
                    )
                )
            }
            cartas.add(
                Carta(
                    CartaTipo.ORGANO,
                    CartaColor.MULTICOLOR,
                    CartaIcono.CUERPO,
                    CartaImagen.CUERPO
                )
            )

            // 17 cartas de virus
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.VIRUS,
                        CartaColor.ROJO,
                        CartaIcono.VIRUS,
                        CartaImagen.VIRUS_ROJO
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.VIRUS,
                        CartaColor.VERDE,
                        CartaIcono.VIRUS,
                        CartaImagen.VIRUS_VERDE
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.VIRUS,
                        CartaColor.AZUL,
                        CartaIcono.VIRUS,
                        CartaImagen.VIRUS_AZUL
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.VIRUS,
                        CartaColor.AMARILLO,
                        CartaIcono.VIRUS,
                        CartaImagen.VIRUS_AMARILLO
                    )
                )
            }
            cartas.add(
                Carta(
                    CartaTipo.VIRUS,
                    CartaColor.MULTICOLOR,
                    CartaIcono.VIRUS,
                    CartaImagen.VIRUS_MULTICOLOR
                )
            )

            // 20 cartas de medicinas
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.MEDICINA,
                        CartaColor.ROJO,
                        CartaIcono.MEDICINA,
                        CartaImagen.MEDICINA_ROJO
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.MEDICINA,
                        CartaColor.VERDE,
                        CartaIcono.MEDICINA,
                        CartaImagen.MEDICINA_VERDE
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.MEDICINA,
                        CartaColor.AZUL,
                        CartaIcono.MEDICINA,
                        CartaImagen.MEDICINA_AZUL
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.MEDICINA,
                        CartaColor.AMARILLO,
                        CartaIcono.MEDICINA,
                        CartaImagen.MEDICINA_AMARILLO
                    )
                )
            }
            repeat(4) {
                cartas.add(
                    Carta(
                        CartaTipo.MEDICINA,
                        CartaColor.MULTICOLOR,
                        CartaIcono.MEDICINA,
                        CartaImagen.MEDICINA_MULTICOLOR
                    )
                )
            }

            // 10 cartas de tratamientos
            repeat(2) {
                cartas.add(
                    Carta(
                        CartaTipo.TRATAMIENTO,
                        CartaColor.BLANCO,
                        CartaIcono.TRATAMIENTO,
                        CartaImagen.TRATAMIENTO_CONTAGIO
                    )
                )
            }
            repeat(3) {
                cartas.add(
                    Carta(
                        CartaTipo.TRATAMIENTO,
                        CartaColor.BLANCO,
                        CartaIcono.TRATAMIENTO,
                        CartaImagen.TRATAMIENTO_ROBO_ORGANO
                    )
                )
            }
            repeat(3) {
                cartas.add(
                    Carta(
                        CartaTipo.TRATAMIENTO,
                        CartaColor.BLANCO,
                        CartaIcono.TRATAMIENTO,
                        CartaImagen.TRATAMIENTO_TRANSPLANTE
                    )
                )
            }
            repeat(1) {
                cartas.add(
                    Carta(
                        CartaTipo.TRATAMIENTO,
                        CartaColor.BLANCO,
                        CartaIcono.TRATAMIENTO,
                        CartaImagen.TRATAMIENTO_ERROR_MEDICO
                    )
                )
            }
            repeat(1) {
                cartas.add(
                    Carta(
                        CartaTipo.TRATAMIENTO,
                        CartaColor.BLANCO,
                        CartaIcono.TRATAMIENTO,
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

    // Devuelve una nueva Baraja con las cartas del descarte reensambladas y barajadas
    fun agregarCartas(cartasParaAgregar: List<Carta>): Baraja {
        var nuevoMazo = ( pila + cartasParaAgregar )
        return this.copy(
            pila = nuevoMazo
        )
    }

    // Devuelve una baraja revuelta
    fun revolver(): Baraja {
        var cartas = this.pila.toMutableList()
        repeat(10) { cartas.shuffle() }
        return this.copy(
            pila = cartas
        )
    }
}