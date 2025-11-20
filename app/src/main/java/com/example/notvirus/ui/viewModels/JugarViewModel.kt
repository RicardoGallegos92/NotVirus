package com.example.notvirus.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.Juego
import com.example.notvirus.data.model.Mano
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class JugarUiState(
    // Tablero
    val isStarted: Boolean = false,
    val isPaused: Boolean = false,
    val juego: Juego,

    // Mano
    val cantCartasSelected: Int = 0,
    val activeBtnPlayCard: Boolean = false,
    val activeBtnDiscardCards: Boolean = false,
)

class JugarViewModel(
    private val partidaGuardada: JugarUiState? = null,
) : ViewModel() {
    private val _uiState = MutableStateFlow(value = JugarUiState(juego = Juego()))
    val uiState: StateFlow<JugarUiState> = _uiState

    init {
        // no sé
    }

    fun startJuego() {
        viewModelScope.launch {
            _uiState.update {
                val nuevoJuego = it.juego.startJuego() // Devuelve un nuevo Juego
                it.copy(
                    juego = nuevoJuego,
                    isStarted = true
                )
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

    fun jugarCarta(){
        viewModelScope.launch {
            _uiState.update{
                var juegoAct = it.juego.passCartaToMesa()
                juegoAct = juegoAct.llenarBaraja()
                it.copy(
                    juego = juegoAct
                )
            }
        }
    }

    fun descartarCartas() {
        println("JugarVM.descartarCartas()")
        viewModelScope.launch {
            _uiState.update {
                var juegoAct = it.juego.passCartasToPilaDescarte()
                juegoAct = juegoAct.llenarBaraja()
                it.copy(juego = juegoAct)
            }
        }
    }

    // MANO

    fun clickedCard(carta: Carta) {
        viewModelScope.launch {
            _uiState.update {
                val nuevoJuego = it.juego.marcarCarta(carta) // devuelve un nuevo Juego
                it.copy(juego = nuevoJuego)
            }
        }
        countCartasSelected()
    }

    fun countCartasSelected() {
        var conteo = 0
        for (i in 0..2) {
            if (_uiState.value.juego.jugadores[1].mano.cartas[i].seleccionada) {
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
        if (_uiState.value.cantCartasSelected == 0) {
            _uiState.update {
                it.copy(
                    activeBtnPlayCard = false,
                    activeBtnDiscardCards = false,
                )
            }
        }
        if (_uiState.value.cantCartasSelected == 1) {
            _uiState.update {
                it.copy(
                    activeBtnPlayCard = true,
                    activeBtnDiscardCards = true,
                )
            }
        }
        if (_uiState.value.cantCartasSelected > 1) {
            _uiState.update {
                it.copy(
                    activeBtnPlayCard = false,
                    activeBtnDiscardCards = true,
                )
            }
        }
    }
}