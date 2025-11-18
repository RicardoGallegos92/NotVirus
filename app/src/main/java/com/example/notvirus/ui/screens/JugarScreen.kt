package com.example.notvirus.ui.screens

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notvirus.ui.items.ManoItem
import com.example.notvirus.ui.items.ManoItemCPU
import com.example.notvirus.ui.items.MesaItem
import com.example.notvirus.ui.viewModels.JugarViewModel

@Composable
fun JugarScreen(
    innerPadding: PaddingValues = PaddingValues(
        top = 56.dp,
        start = 16.dp,
        end = 16.dp,
        bottom = 16.dp
    ),
    juegoViewModel: JugarViewModel = viewModel(),
) {
    // aplicar viewModel
    val uiState by juegoViewModel.uiState.collectAsStateWithLifecycle()

    val isStarted = uiState.isStarted

    val isPaused = uiState.isPaused

    val juego = uiState.juego

    Box(
        modifier = Modifier
            .padding(paddingValues = innerPadding)
            .fillMaxSize(),
    ) {
        Column(
            // TABLERO / Pantalla completa
            modifier = Modifier
                .background(color = Color(53, 101, 77))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            if (!isStarted) {
                Button(
                    onClick = { juegoViewModel.cargarJuego() }
                ) { Text("Empezar Juego") }
            } else {
                if (isPaused) {
                    // boton de UnPause
                    Button(
                        onClick = { juegoViewModel.unPauseJuego() }
                    ) {
                        Text(text = "Continuar")
                    }
                } else {
                    Column(
                        // Zona Jugador CPU
                        modifier = Modifier
                            .background(color = Color(3, 70, 148))
                            .fillMaxWidth()
                            .weight(2f),
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
                            ManoItemCPU(mano = juego.jugadores[0].mano)
                        }
                        Row(
                            // Jugador CPU - Mesa
                            modifier = Modifier
                                .background(color = Color(3, 70, 148))
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            MesaItem(mesa = juego.jugadores[0].mesa)
                        }
                    }
                    Row(
                        // centro mesa (zona Neutral)
                        modifier = Modifier
                            .background(color = Color(0, 0, 0, 256))
                            .fillMaxWidth()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        // baraja
                        PilaDeCartas(
                            texto = "Baraja",
                            cantidadCartas = juego.baraja.mazo.size,
                        )
                        // boton de Pausa
                        Button(onClick = { juegoViewModel.pauseJuego() })
                        { Text(text = "Pausar") }
                        // pila descarte
                        PilaDeCartas(
                            texto = "Descarte",
                            cantidadCartas = juego.pilaDescarte.pila.size,
                        )
                    }
                    Column(
                        // Zona Jugador Humano
                        modifier = Modifier
                            .background(color = Color(128, 128, 0))
                            .fillMaxWidth()
                            .weight(2f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Row(
                            // Jugador Humano - Mesa
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                        ) {
                            MesaItem(mesa = juego.jugadores[1].mesa)
                        }
                        Row(
                            // Jugador Humano - Mano
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                        ) {
                            ManoItem(
                                mano = juego.jugadores[1].mano,
                                play = { },
                                discard = {
                                    juegoViewModel.descartarCartas()
                                },
                                useButtons = true,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PilaDeCartas(
    texto: String = "Nombre Pila",
    cantidadCartas: Int = 30,
) {
    val ANCHO_PILA: Int = 100
    val ALTO_PILA: Int = (68 * 2)
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
            color = Color(0, 0, 0),
            fontSize = FUENTE_TEXTO.sp,
        )
        Column(
            // tamaño total en Negro
            modifier = Modifier
                .background(color = Color(0, 0, 0))
                .width(ANCHO_PILA.dp)
                .height(ALTO_PILA.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                // representacion de cartas contenidas en Blancio
                modifier = Modifier
                    .background(color = Color(255, 255, 255))
                    .fillMaxWidth()
                    .height((cantidadCartas * 2).dp),
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