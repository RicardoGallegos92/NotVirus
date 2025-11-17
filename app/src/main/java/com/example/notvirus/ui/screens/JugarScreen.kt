package com.example.notvirus.ui.screens

import android.widget.ProgressBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notvirus.ui.items.JuegoItem
import com.example.notvirus.ui.viewModels.JugarViewModel

@Composable
fun JugarScreen(
    innerPadding: PaddingValues,
    juegoViewModel: JugarViewModel = viewModel()
) {
    // aplicar viewModel
    val uiState by juegoViewModel.uiState.collectAsStateWithLifecycle()

    val isLoading = uiState.isLoading

    val juego = uiState.juego

    Box(
        modifier = Modifier
            .padding(paddingValues = innerPadding)
            .fillMaxSize()
    ) {

        if(isLoading){
            CircularProgressIndicator()
        }else{
            JuegoItem(juego = juego)
        }
    }
}