package com.example.notvirus.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.notvirus.data.model.Juego
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class JugarUiState(
    val isLoading: Boolean = false,
    val juego: Juego = Juego()
)

class JugarViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(value = JugarUiState())
    val uiState: StateFlow<JugarUiState> = _uiState

    init {
        // no sé
    }

    fun cargarJuego() {
        _uiState.update {
            it.copy(
                isLoading = true,
            )
        }

    }
}