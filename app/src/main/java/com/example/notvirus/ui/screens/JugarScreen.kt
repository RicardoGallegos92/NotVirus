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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notvirus.ui.items.ManoItem
import com.example.notvirus.ui.items.MesaItem
import com.example.notvirus.ui.viewModels.JugarViewModel
import org.intellij.lang.annotations.JdkConstants


@Composable
fun JugarScreen(
    innerPadding: PaddingValues,
    juegoViewModel: JugarViewModel = viewModel(),
) {
    // aplicar viewModel
    val uiState by juegoViewModel.uiState.collectAsStateWithLifecycle()

    val isStarted = uiState.isStarted

    val juego = uiState.juego

    Box(
        modifier = Modifier
            .padding(paddingValues = innerPadding)
            .padding(10.dp)
            .fillMaxSize(),
    ) {
        if (!isStarted) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(
                    onClick = { juegoViewModel.cargarJuego() }
                ) { Text("Empezar Juego") }
            }
        } else {
            //JuegoItem(juego = juego)
            Column(
                // TABLERO
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Row(
                    // JUGADOR CONTRARIO
                    modifier = Modifier
                        .background(color = Color(3, 70, 148))
                        .padding(horizontal = 5.dp)
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (juego.jugador1Activo) {
                        MesaItem(juego.jugadores[0].mesa)
                    } else {
                        MesaItem(juego.jugadores[1].mesa)
                    }
                }
                Row(
                    // centro mesa
                    modifier = Modifier
                        .background(color = Color(53, 101, 77))
                        .padding(
                            vertical = 10.dp,
                            horizontal = 3.dp
                        )
                        .fillMaxWidth()
                        .weight(4f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    // baraja
                    PilaDeCartas(
                        texto = "Baraja",
                        cantidadCartas = juego.baraja.mazo.size,
                    )
                    // pila descarte
                    PilaDeCartas(
                        texto = "Descarte",
                        cantidadCartas = juego.pilaDescarte.pila.size,
                    )
                }
                Row(
                    // abajo (mano del jugador en turno)
                    modifier = Modifier
                        .background(color = Color(128, 128, 0))
                        .padding(horizontal = 0.dp)
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    if (juego.jugador1Activo) {
                        ManoItem(
                            juego.jugadores[1].mano
                        )
                    } else {
                        ManoItem(
                            juego.jugadores[0].mano
                        )
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
    val ANCHO_PILA: Int = 80
    val ALTO_PILA: Int = (68 * 2)
    val FUENTE_NUMERO: Int = 24
    val FUENTE_TEXTO: Int = 20
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
                .height(ALTO_PILA.dp)
            ,
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