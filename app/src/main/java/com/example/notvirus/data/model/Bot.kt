package com.example.notvirus.data.model

import android.util.Log
import android.util.Log.e

/** Nombre Clave:
 * Comportamiento:
 * ->
 */

enum class Bot(val play: (Juego) -> Juego) {
    COPO({ juego -> copo(juego) }),
    MANU({ juego -> manu(juego) }),
    NICO({ juego -> nico(juego) }),
    FENA({ juego -> fena(juego) }),
    SANSON({ juego -> sanson(juego) }),
}

/** Nombre Clave: Copo
 * Comportamiento:
 * Intentará jugar cada carta en su mano
 * -> si ninguna puede ser jugada
 *   => Descarta la Mano completa
 */
fun copo(juego: Juego): Juego {
    val TAG = "Bot COPO"
    val jugadorActivo = juego.getJugadorByID(juego.jugadorActivoID)
    try {
        for (index in 0..3) {
            if (index == 3) {
                throw ImposibleJugarCarta()
            }
            Log.i(TAG, "intento de jugar carta: ${index+1}")
            val cartaID = jugadorActivo.getCartaManoByIndex(index).id
            // marcar la carta
            Log.i(TAG, "${jugadorActivo.getCartaManoByID(cartaID).tipo}/${jugadorActivo.getCartaManoByID(cartaID).color}")
            val juegoPrevio = juego.marcarCarta(cartaID)

            val juegoNuevo = juegoPrevio.usarTurno(jugarCarta = true)

            if (juegoNuevo != juegoPrevio) {
                return juegoNuevo
            } else {
                // true => la jugada falló
                // desmarcar la carta
                juego.marcarCarta(cartaID)
            }
        }
    } catch (e: Exception) {
        Log.i(TAG, e.message.toString())
        var juegoNuevo: Juego = juego.copy()
        for (index in 0..2) {
            val cartaID = jugadorActivo.getCartaManoByIndex(index).id
            // marcar la carta
            juegoNuevo = juegoNuevo.marcarCarta(cartaID)
        }
        Log.i(TAG, "Se decarta Mano completa")
        juegoNuevo = juegoNuevo.usarTurno(descartarCarta = true)
        return juegoNuevo
    }
    Log.w(TAG, "No debíese haber pasado por aquí, WTF!")
    return juego
}

/** Nombre Clave: Manu
 * Comportamiento: Enfocado en Ataque
 * -> Virus
 * -> Organo
 * -> Medicina
 */
fun manu(juego: Juego): Juego {
    return juego
}

/** Nombre Clave: Nico
 * Comportamiento: Enfocado en Defensa
 * -> Medicina
 * -> Virus
 * -> Organo
 */
fun nico(juego: Juego): Juego {
    return juego
}

/** Nombre Clave: Feña
 * Comportamiento: Enfocado en Perder:
 * -> No usa Tratamientos
 * -> Organo
 * -> Medicina
 * -> Virus
 */
fun fena(juego: Juego): Juego {
    return juego
}

/** Nombre Clave: Sanson
 * Comportamiento: Analizar "turnos para ganar" de oponente(s):
 * -> 1 -> Robo_Organo, Error_Medico
 * -> 2 -> Virus, Trasplante
 * -> 3 -> Contagio
 * -> 4 -> Organo, Medicina, Guante_Latex
 * -> 5 -> Descartar Mano
 */
fun sanson(juego: Juego): Juego {
    return juego
}
