package com.example.notvirus.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notvirus.data.model.Juego
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class JugarUiState(
    val isStarted: Boolean = false,
    val juego: Juego = Juego(),
)

class JugarViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(value = JugarUiState())
    val uiState: StateFlow<JugarUiState> = _uiState

    init {
        // no sé
    }

    fun cargarJuego() {
        // corrutina wiiiii :3
        viewModelScope.launch {
            // let's GO !!!
            _uiState.value.juego.startJuego()
            _uiState.update {
                it.copy(
                    isStarted = true
                )
            }
        }
    }
}