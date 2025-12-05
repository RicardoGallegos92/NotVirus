package com.example.notvirus.data.model

/** Nombre Clave:
 * Comportamiento:
 * ->
 */


enum class Bot(val play: (Jugador, Int) -> String) {
    COPO({ jugador, int -> copo(jugador, int) }),
    MANU({ jugador, int -> manu(jugador, int) }),
    NICO({ jugador, int -> nico(jugador, int) }),
    FENA({ jugador, int -> fena(jugador, int) }),
    SANSON({ jugador, int -> sanson(jugador, int) }),
}

/** Nombre Clave: Copo
 * Comportamiento:
 * Intentará jugar cada carta en su mano
 * -> si ninguna puede ser jugada
 *   => Descarta la Mano completa
 */
fun copo(jugador: Jugador, intento: Int): String {
    if(intento == 3){
        throw ImposibleJugarCarta()
    }
    return  jugador.mano.cartas.elementAt(intento).id
}

/** Nombre Clave: Manu
 * Comportamiento: Enfocado en Ataque
 * -> Virus
 * -> Organo
 * -> Medicina
 */
fun manu(jugador: Jugador, intento: Int): String {
    return ""
}

/** Nombre Clave: Nico
 * Comportamiento: Enfocado en Defensa
 * -> Medicina
 * -> Virus
 * -> Organo
 */
fun nico(jugador: Jugador, intento: Int): String {
    return ""
}

/** Nombre Clave: Feña
 * Comportamiento: Enfocado en Perder:
 * -> No usa Tratamientos
 * -> Organo
 * -> Medicina
 * -> Virus
 */
fun fena(jugador: Jugador, intento: Int): String {
    return ""
}

/** Nombre Clave: Sanson
 * Comportamiento: Analizar "turnos para ganar" de oponente(s):
 * -> 1 -> Robo_Organo, Error_Medico
 * -> 2 -> Virus, Trasplante
 * -> 3 -> Contagio
 * -> 4 -> Organo, Medicina, Guante_Latex
 * -> 5 -> Descartar Mano
 */
fun sanson(jugador: Jugador, intento: Int): String {
    return ""
}