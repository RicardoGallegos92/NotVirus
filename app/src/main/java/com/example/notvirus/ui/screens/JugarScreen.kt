package com.example.notvirus.ui.screens

import android.R.attr.onClick
import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notvirus.R
import com.example.notvirus.data.model.Carta
import com.example.notvirus.ui.items.CartaItem
import com.example.notvirus.ui.items.ManoItem
import com.example.notvirus.ui.items.ManoItemCPU
import com.example.notvirus.ui.items.MesaItem
import com.example.notvirus.ui.viewModels.JugarViewModel
import com.example.notvirus.BuildConfig
import com.example.notvirus.data.model.Juego
import com.example.notvirus.data.model.Jugador
import com.example.notvirus.data.model.Mesa
import com.example.notvirus.ui.components.MyColumn
import com.example.notvirus.ui.viewModels.JugarUiState

@Composable
fun JugarScreen(
    innerPadding: PaddingValues,
    onNavigateBack: () -> Unit,
    juegoViewModel: JugarViewModel = viewModel(),
) {
    // aplicar viewModel
    val uiState by juegoViewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = uiState.isLoading
    val errorMsg = uiState.errorMsg
    val isStarted = uiState.isStarted
    val isPaused = uiState.isPaused
    val isOver = uiState.isOver
    val juego = uiState.juego

    Box(
        modifier = Modifier
            .padding(paddingValues = innerPadding)
            .fillMaxSize(),
    ) {
        Column(
            // TABLERO / Pantalla completa
            modifier = Modifier
                .background(color = colorResource(R.color.mesa))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            /*
            // aplicar este truquete
            when{
                true -> {}
                else -> {}
            }
            */
            if (isLoading) {
                CircularProgressIndicator(
                    color = colorResource(R.color.progreso)
                )
                Text(
                    text = "Cargando",
                    color = colorResource(R.color.progreso)
                )
            } else {
                if ( errorMsg.isNullOrEmpty() ) {
                    if (isOver) {
                        MyColumn(
                            textos = listOf(
                                "Ganador: ${juego.getJugadorByID(juego.jugadorActivoID!!).nombre}",
                                stringResource(R.string.juego_ganador),
                            )
                        )
                        Button(
                            onClick = { juegoViewModel.startJuego() },
                        ) {
                            Text(text = stringResource(R.string.juego_iniciar))
                        }
                        Button(
                            onClick = onNavigateBack,
                        ) {
                            Text(text = stringResource(R.string.to_pantalla_Inicio))
                        }
                    } else {
                        if (isPaused) {
                            MenuPausa(
                                linkVolver = onNavigateBack,
                                actionPausar = { juegoViewModel.unPauseJuego() },
                                actionContinuar = { juegoViewModel.startJuego() },
                            )
                        } else {
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
                                    activeBtnPlayCard = uiState.activeBtnPlayCard,
                                    activeBtnDiscardCards = uiState.activeBtnDiscardCards,
                                )
                            }
                        }
                    }
                } else {
                    println("Error -> ${errorMsg}")
                    MensajeError(errorMsg)
                }
            }
        }
    }
}

@Composable
fun MensajeError(error: String) {
    MyColumn(listOf(error))
}

@Composable
fun ZonaJugadorArriba(
    jugador: Jugador,
) {
    Column(
        modifier = Modifier
            .background(color = Color(3, 70, 148))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Row(
            // Jugador CPU - Mano
            modifier = Modifier
                .background(color = Color(3, 70, 148))
                .fillMaxWidth()
                .weight(1f)
        ) {
            ManoItemCPU(mano = jugador.mano)
        }
        Row(
            // Jugador CPU - Mesa
            modifier = Modifier
                .background(color = Color(3, 70, 148))
                .fillMaxWidth()
                .weight(1f)
        ) {
            MesaItem(mesa = jugador.mesa)
        }
    }
}

@Composable
fun ZonaCentral(
    juego: Juego,
    juegoViewModel: JugarViewModel,
) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        // baraja
        PilaDeCartas(
            texto = "Baraja",
            cantidadCartas = juego.baraja.pila.size,
        )
        println("Baraja: ${juego.baraja.pila.size}")
        // boton de Pausa
        Button(onClick = { juegoViewModel.pauseJuego() })
        { Text(text = "Pausar") }
        // pila descarte
        PilaDeCartas(
            texto = "Descarte",
            cantidadCartas = juego.pilaDescarte.pila.size,
        )
        println("Descarte: ${juego.pilaDescarte.pila.size}")
    }
}

@Composable
fun ZonaJugadorAbajo(
    jugador: Jugador,
    viewModel: JugarViewModel,
    uiState: JugarUiState,
    activeBtnPlayCard: Boolean,
    activeBtnDiscardCards: Boolean,
) {
    Column(
        modifier = Modifier
            .background(color = Color.Transparent)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Row(
            // Jugador Humano - Mesa
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            MesaItem(mesa = jugador.mesa)
        }
        Row(
            // Jugador Humano - Mano
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            ManoItem(
                mano = jugador.mano,
                viewModel = viewModel,
                useButtons = true,
                activeBtnPlayCard = activeBtnPlayCard,
                activeBtnDiscardCards = activeBtnDiscardCards,
            )
        }
    }
}

@Composable
fun PilaDeCartas(
    texto: String = "Nombre Pila",
    cantidadCartas: Int = 30,
) {
    val ANCHO_PILA: Int = 100
    val ALTO_CARTA = 2
    val ALTO_PILA: Int = (68 * ALTO_CARTA)
    val FUENTE_NUMERO: Int = 20
    val FUENTE_TEXTO: Int = 18
    Column(
        // Cajón
        modifier = Modifier
            .background(color = Color.Transparent)
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = texto,
            color = colorResource(R.color.black),
            fontSize = FUENTE_TEXTO.sp,
        )
        Column(
            // tamaño total en Negativo
            modifier = Modifier
                .background(color = colorResource(R.color.black))
                .width(ANCHO_PILA.dp)
                .height(ALTO_PILA.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                // representacion de cartas contenidas en color carta
                modifier = Modifier
                    .background(color = colorResource(R.color.white))
                    .fillMaxWidth()
                    .height((cantidadCartas * ALTO_CARTA).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) { }
        }
        Text(
            text = cantidadCartas.toString(),
            color = Color(0, 0, 0),
            fontSize = FUENTE_NUMERO.sp,
        )
    }
}

@Composable
fun MenuPausa(
    linkVolver: () -> Unit = {},
    actionPausar: () -> Unit = { },
    actionContinuar: () -> Unit = { },
) {
    Button(
        onClick = { linkVolver() },
    ) {
        Text(text = stringResource(R.string.to_pantalla_Inicio))
    }
    // boton de UnPause
    Button(
        onClick = { actionPausar() }
    ) {
        Text(text = stringResource(R.string.juego_continuar))
    }
    Button(
        modifier = Modifier
            .background(color = Color(255, 0, 0)),
        onClick = { actionContinuar() }
    ) {
        Text(text = stringResource(R.string.juego_reiniciar))
    }
}