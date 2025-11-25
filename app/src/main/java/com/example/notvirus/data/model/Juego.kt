package com.example.notvirus.data.model

data class Juego(
    val jugadores: List<Jugador>,
    val baraja: Baraja,
    val pilaDescarte: PilaDescarte,

    val jugadorActivo: Jugador?,
    val jugadorGanador: Jugador?,

    val maxCartasEnMano: Int,
) {
    constructor() : this(
        jugadores = listOf(Jugador(nombre = "CPU"), Jugador(nombre = "Jugador1")),
        baraja = Baraja(),
        pilaDescarte = PilaDescarte(),

        jugadorActivo = null,
        jugadorGanador = null,

        maxCartasEnMano = 3,
    )

    // empezarJuego: Devuelve un nuevo Juego con manos repartidas y primer jugador activo
    fun empezarJuego(): Juego {
        println("Juego.empezarJuego()")
        var barajaActual = baraja // Mantén un registro de la baraja actualizada
        val jugadoresConCartas = jugadores.map { jugador ->
            val (cartasParaJugador, nuevaBaraja) = barajaActual.pedirCartas(maxCartasEnMano) // <-- solicitar cartas para la mano
            barajaActual = nuevaBaraja // Actualiza la baraja para el siguiente jugador
            jugador.recibirCartasToMano(cartasParaJugador) // <-- Pasa la lista de cartas
        }

        val nuevoJugadorActivo = pasarTurno()

        return this.copy(
            jugadores = jugadoresConCartas,
            baraja = barajaActual, // <-- Baraja-Actualizada
            jugadorActivo = jugadoresConCartas.filter{it.id == nuevoJugadorActivo.id}[0],
        )
    }

    fun hayGanador(): Pair<Boolean, Jugador?> {
        println("Juego.hayGanador()")
        // Lógica para determinar ganador
        // -> el jugador ganó si el conteo (turnosParaGanar) de su mesa es 0
        val jugadorGanador = jugadores.filter { jugador: Jugador ->
            jugador.mesa.turnosParaGanar == 0
        }
        return if (jugadorGanador.isEmpty()) {
            // vacio -> no ganador
            Pair(false, null)
        } else {
            Pair(true, jugadorGanador[0])
        }
    }

    fun jugarCarta(): Juego {
        println("Juego.jugarCarta()")
        val cartaJugada: Carta = tomarCartaJugada()
        // distinguir entre TRATAMIENTO y otras
        when(cartaJugada.tipo){
            CartaTipo.TRATAMIENTO -> {
                // activar Efecto
                // quitar 'Carta' de la 'Mano'
                // enviar 'Carta' a 'PilaDescarte'
                return descartarDesdeMano()
            }
            else -> {
                pasarCartaToMesa(
                    cartaJugada = cartaJugada,
                    jugadorObjetivo = pasarTurno() // (provisorio) -> se debe agregar funcion para seleccionar objetivo
                )
            }
        }

        return this.copy(

        )
    }
    fun pasarCartaToMesa(cartaJugada: Carta, jugadorObjetivo:Jugador): Juego {
        println("Juego.pasarCartaToMesa()")
        // pasarCartaToMesa: Devuelve un 'Juego-Actualizado':
        // por cohesión esto debería recibir la 'cartaJugada' y el 'jugadorObjetivo' y usar el 'jugadorActivo'

        // pasar la 'cartaJugada' a la mesa del 'jugadorObjetivo'
        val jugadorObjetivoActualizado = jugadorObjetivo.agregaCartaToMesa(cartaJugada)
        // quitar la 'cartaJugada' de la mano del 'jugadorActivo'
        var (_,jugadorActivoActualizado) = jugadorActivo!!.descartarCartas()
        // pedir 'cartaNueva' para la mano del 'jugadorActivo'
        val (nuevasCartasMano, barajaActualizada) = baraja.pedirCartas((maxCartasEnMano - jugadorActivoActualizado.mano.cartas.size))
        // pasar la 'cartaNueva' al 'jugadorActivo'
        jugadorActivoActualizado = jugadorActivoActualizado.recibirCartasToMano(nuevasCartasMano)

        // actualizar jugadores Activo y Objetivo para la UI
        val jugadoresActualizados = jugadores.map { jugador ->
            when (jugador.id) {
                jugadorActivo?.id -> jugadorActivoActualizado
                jugadorObjetivo.id -> jugadorObjetivoActualizado
                else -> jugador
            }
        }

        return this.copy(
            jugadores = jugadoresActualizados,
            baraja = barajaActualizada
        )
    }

    fun descartarDesdeMano(): Juego {
        println("Juego.descartarDesdeMano()")
//        println("jugadorActivo!!.nombre: ${jugadorActivo!!.nombre}")
//        println("-> mano.size: ${jugadorActivo!!.mano.cartas.size}")
        // no hace falta lógica extra, las cartas pasan de la mano a la pila
        val (cartasDescartadas, jugadorActivoActualizado) = getJugadorActivo(jugadorActivo!!.id).descartarCartas()
        println("jugadorActivoActualizado: ${jugadorActivoActualizado.nombre} | Cartas Mano: ${jugadorActivoActualizado.mano.cartas.size}")
        // baraja actualizada y cartas extraidas
        val (nuevasCartasMano, barajaActualizada) = baraja.pedirCartas(cartasDescartadas.size)
        // jugador recibe las cartas tomadas de la baraja
        val jugadorConNuevasCartas = jugadorActivoActualizado.recibirCartasToMano(nuevasCartasMano)
        println("jugadorConNuevasCartas: ${jugadorConNuevasCartas.nombre} | Cartas Mano: ${jugadorConNuevasCartas.mano.cartas.size}")
        // actualizar jugadores para la UI
        val jugadoresActualizados = jugadores.map { jugador ->
            if (jugador.id == jugadorActivo.id){
                //println("Jugadores -> actualizado: ${jugadorConNuevasCartas.nombre} | Cartas Mano: ${jugadorConNuevasCartas.mano.cartas.size}")
                jugadorConNuevasCartas
            } else{
                //println("Jugadores -> original: ${jugador.nombre} | Cartas Mano: ${jugador.mano.cartas.size}")
                jugador
            }
        }

        return this.copy(
            jugadores = jugadoresActualizados,
            pilaDescarte = pilaDescarte.agregarCartas(cartasDescartadas),
            baraja = barajaActualizada,
        )
    }

    fun marcarCarta(carta: Carta): Juego {
        println("Juego.marcarCarta()")
    // cambia el estado "selecciona" de la carta entre "true" y "false"
        val jugadorActualizado = jugadores[1].marcarCartaEnMano(carta) // cambiar a 'JugadorActivo'
        val jugadoresActualizados = jugadores.toMutableList()
        jugadoresActualizados[1] = jugadorActualizado

        return this.copy(
            jugadores = jugadoresActualizados
        )
    }

    fun tomarCartaJugada(): Carta {
        println("Juego.tomarCartaJugada()")
        val cartaJugada = jugadorActivo!!.entregarCartaJugada()

        return cartaJugada
    }

    fun llenarBaraja(): Juego {
        //println("Juego.llenarBaraja()")
        if (baraja.pila.size < maxCartasEnMano) {
            val (cartasPila, pilaDescarteActualizada) = pilaDescarte.tomarTodasLasCartas()
            val cartasPilaMutable = cartasPila.map { carta: Carta ->
                carta.copy(
                    estaSeleccionada = false,
                    esInmune = false,
                )
            }
            return this.copy(
                baraja = baraja.agregarCartas(cartasPilaMutable),
                pilaDescarte = pilaDescarteActualizada
            )
        } else {
            //println("Juego.llenarBaraja() : else -> return")
            return this
        }
    }

    // cambiar jugador en turno (escalable)
    fun pasarTurno(): Jugador {
        println("Juego.pasarTurno()")
        if (jugadorActivo == null) {
            // jugador inincial por defecto
            return jugadores[1] // -> el player humano
        } else {
            val index = jugadores.indexOf(getJugadorActivo(jugadorActivo.id))
            return jugadores[ (index + 1) % jugadores.size ]
        }
    }

    fun getJugadorActivo(id:String):Jugador{
        println("Juego.getJugadorActivo()")
        return jugadores.filter{ jugador ->
            jugador.id == id
        }[0]
    }
}