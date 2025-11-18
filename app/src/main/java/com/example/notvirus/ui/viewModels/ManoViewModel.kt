package com.example.notvirus.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ManoUiState(
    val selectedCartas: MutableList<Boolean> = mutableListOf(false, false, false),
    val cantCartasSelected: Int = 0,
    val activeBtnPlayCard: Boolean = false,
    val activeBtnDiscardCards: Boolean = false,
)

class ManoViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(value = ManoUiState())

    val uiState: StateFlow<ManoUiState> = _uiState

    fun clickedCard(index: Int) {
        viewModelScope.launch {
            val nuevoSelectedCartas = _uiState.value.selectedCartas.toMutableList()
            nuevoSelectedCartas[index] = !nuevoSelectedCartas[index]
            _uiState.update {
                it.copy(
                    selectedCartas = nuevoSelectedCartas
                )
            }

        }
        println(_uiState.value.selectedCartas)
        countCartasSelected()
    }

    fun countCartasSelected() {
        var conteo = 0
        for (i in 0..2){
            if (_uiState.value.selectedCartas[i]) {
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
                    activeBtnDiscardCards = false,
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