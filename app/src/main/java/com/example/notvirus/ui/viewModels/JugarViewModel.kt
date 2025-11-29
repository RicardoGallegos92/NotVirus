package com.example.notvirus.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.Juego
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class JugarUiState(
    // Tablero
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val isStarted: Boolean = false,
    val isPaused: Boolean = false,
    val isOver: Boolean = false,
    val juego: Juego = Juego(),

    // Mano
    val cantCartasSelected: Int = 0,
    val activeBtnPlayCard: Boolean = false,
    val activeBtnDiscardCards: Boolean = false,
)

class JugarViewModel(
//    private val partidaGuardada: JugarUiState? = null,
) : ViewModel() {
    private val _uiState = MutableStateFlow(value = JugarUiState())
    val uiState: StateFlow<JugarUiState> = _uiState

    init {
        startJuego()
    }

    // USUARIO
    fun startJuego() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                )
            }
            try {
                val nuevoJuego = Juego()
                val nuevaPartida = async { nuevoJuego.empezarJuego() }.await()
                delay(500) // <-- dar ilusion de carga
                _uiState.update {
                    it.copy(
                        juego = nuevaPartida,
                        isStarted = true,
                        isLoading = false,

                        isPaused = false,
                        isOver = false,
                        cantCartasSelected = 0,
                        activeBtnPlayCard = false,
                        activeBtnDiscardCards = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isStarted = false,
                        errorMsg = e.message,
                    )
                }
            }
        }
    }

    fun pauseJuego() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isPaused = true
                )
            }
        }
        // mandar a guardar
    }

    fun unPauseJuego() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isPaused = false
                )
            }
        }
    }

    // TURNO
    fun jugarCarta() {
        viewModelScope.launch {
            _uiState.update {
                val juegoActualizado = async { it.juego.usarTurno(jugarCarta = true) }.await()
                val isOver = juegoActualizado.jugadorGanadorID.isNotEmpty()
                it.copy(
                    juego = juegoActualizado,
                    isOver = isOver,
                )
            }
        }
        countCartasSelected()
    }

    fun descartarCartas() {
        println("JugarVM.descartarCartas()")
        viewModelScope.launch {
            _uiState.update {
                var juegoAct = async { it.juego.usarTurno(descartarCarta = true) }.await()
                it.copy(
                    juego = juegoAct,
                )
            }
        }
        countCartasSelected()
    }

    // MANO
    fun clickedCard(carta: Carta) {
        viewModelScope.launch {
            _uiState.update {
                val nuevoJuego = it.juego.marcarCarta(carta) // devuelve un nuevo Juego
                it.copy(
                    juego = nuevoJuego
                )
            }
        }
        countCartasSelected()
    }

    fun countCartasSelected() {
        // println("JugarVM.countCartasSelected()")
        var conteo = 0
        val mano = _uiState.value.juego.jugadores[1].mano.cartas

        for (carta in mano) {
            if (carta.estaSeleccionada) {
                conteo++
            }
        }

        _uiState.update {
            it.copy(
                cantCartasSelected = conteo
            )
        }
        activeButton()
    }

    private fun activeButton() {
        when (_uiState.value.cantCartasSelected) {
            0 -> {
                _uiState.update {
                    it.copy(
                        activeBtnPlayCard = false,
                        activeBtnDiscardCards = false,
                    )
                }
            }

            1 -> {
                _uiState.update {
                    it.copy(
                        activeBtnPlayCard = true,
                        activeBtnDiscardCards = true,
                    )
                }
            }

            else -> {
                _uiState.update {
                    it.copy(
                        activeBtnPlayCard = false,
                        activeBtnDiscardCards = true,
                    )
                }
            }
        }
    }
}