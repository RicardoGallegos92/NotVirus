package com.example.notvirus.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notvirus.R
import com.example.notvirus.data.model.Bot
import com.example.notvirus.data.model.Juego
import com.example.notvirus.ui.components.BtnSeleccion
import com.example.notvirus.ui.components.MensajeError
import com.example.notvirus.ui.components.MenuJuegoTerminado
import com.example.notvirus.ui.components.MenuPausa
import com.example.notvirus.ui.components.ZonaCentral
import com.example.notvirus.ui.components.ZonaJugadorAbajo
import com.example.notvirus.ui.components.ZonaJugadorArriba
import com.example.notvirus.ui.viewModels.JugarViewModel

@Composable
fun Jugar1PlayerScreen(
    innerPadding: PaddingValues,
    navigateToInicio: () -> Unit,
    navigateBack: () -> Unit = {},
    bot: Bot,
    juegoViewModel: JugarViewModel = viewModel(),
) {
    // aplicar viewModel
    val uiState by juegoViewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = uiState.isLoading
    val errorMsg = uiState.errorMsg
//    val isStarted = uiState.isStarted
    val isPaused = uiState.isPaused
    val isOver = uiState.isOver
    val juego = uiState.juego
    val botEnabled = ( juego.jugadorActivoID == juego.jugadores[0].id && !isOver )

    Box(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        BtnSeleccion(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .zIndex(zIndex = if(botEnabled){2f}else{0f}),
            texto = bot.name,
            onClick = { juegoViewModel.jugadaBot(turnoBot(bot, juego)) },
            enabled = botEnabled
        )
        Column(
            // TABLERO / Pantalla completa
            modifier = Modifier
                .zIndex(1f)
                .background(color = colorResource(R.color.mesa))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(color = colorResource(R.color.progreso))
                    Text(
                        text = "Cargando",
                        color = colorResource(R.color.progreso)
                    )
                }

                errorMsg.isNullOrEmpty() -> {
                    when {
                        isOver -> {
                            MenuJuegoTerminado(
                                actionBack = { navigateBack() },
                                nombreGanador = juego.getJugadorByID(juego.jugadorGanadorID).nombre,
                                navigateToInicio = { navigateToInicio() },
                                revancha = { juegoViewModel.startJuego() },
                                onePlayer = true
                            )
                        }

                        isPaused -> {
                            MenuPausa(
                                actionSalir = { navigateToInicio() },
                                actionBack = { navigateBack() },
                                actionContinuar = { juegoViewModel.unPauseJuego() },
                                actionReiniciar = { juegoViewModel.startJuego() },
                                onePlayer = true,
                            )
                        }

                        else -> {
                            Column(
                                // Zona Jugador CPU
                                modifier = Modifier
                                    .background(color = colorResource(R.color.zona_cpu))
                                    .fillMaxWidth()
                                    .weight(2f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                ZonaJugadorArriba(
                                    jugador = juego.jugadores[0],
                                    viewModel = juegoViewModel,
                                    activeBtns = false, //(juego.jugadorActivoID == juego.jugadores[0].id),
                                    activeBtnPlayCard = uiState.activeBtnPlayCard,
                                    activeBtnDiscardCards = uiState.activeBtnDiscardCards,
                                )
                            }
                            Column(
                                // Zona Central
                                modifier = Modifier
                                    .background(color = colorResource(R.color.zona_central))
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                ZonaCentral(
                                    juego = juego,
                                    juegoViewModel = juegoViewModel,
                                )
                            }
                            Column(
                                // Zona Jugador Humano
                                modifier = Modifier
                                    .background(color = colorResource(R.color.zona_jugador))
                                    .fillMaxWidth()
                                    .weight(2f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                ZonaJugadorAbajo(
                                    jugador = juego.jugadores[1],
                                    viewModel = juegoViewModel,
                                    uiState = uiState,
                                    activeBtns = true, //(juego.jugadorActivoID == juego.jugadores[1].id),
                                    activeBtnPlayCard = uiState.activeBtnPlayCard,
                                    activeBtnDiscardCards = uiState.activeBtnDiscardCards,
                                )
                            }
                        }
                    }
                }

                else -> {
                    Log.e("Jugar1PlayerScreen", "Error -> $errorMsg")
                    MensajeError(errorMsg)
                }
            }
        }
    }
}

fun turnoBot(bot: Bot, juego:Juego): Juego {
    return bot.play(juego)
}
