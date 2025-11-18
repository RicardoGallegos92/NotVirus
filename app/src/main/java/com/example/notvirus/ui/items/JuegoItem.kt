/*
package com.example.notvirus.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notvirus.data.model.Juego

@Preview(showBackground = true)
@Composable
fun JuegoItem(
    juego: Juego = Juego(),
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            // Arriba (fondo)
            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth()
            ) {
                if (juego.jugador1Activo) {
                    MesaItem(juego.jugadores[0].mesa)
                } else {
                    MesaItem(juego.jugadores[1].mesa)
                }
            }
            // centro (descarte)
            Row(
                modifier = Modifier
                    .padding(
                        vertical = 10.dp,
                        horizontal = 3.dp
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                // tamaño baraja
                Column(
                    modifier = Modifier
                        .background(color = Color(0, 0, 0))
                        .width(40.dp)
                        .height(68.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(color = Color(255, 255, 255))
                            .fillMaxWidth()
                            .height(juego.baraja.mazo.size.dp)
                    ) { }
                }
                // tamaño descarte
                Column(
                    modifier = Modifier
                        .background(color = Color(0, 0, 0))
                        .width(40.dp)
                        .height(68.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(color = Color(255, 255, 255))
                            .fillMaxWidth()
                            .height(juego.pilaDescarte.pila.size.dp)
                    ) { }
                }
            }
            // abajo (mano del jugador)
            Row(
                modifier = Modifier
                    .padding(horizontal = 0.dp)
                    .fillMaxWidth()
            ) {
                if (juego.jugador1Activo) {
                    ManoItem(
                        mano = juego.jugadores[1].mano,
                        discard = { juego.passCartasToPilaDescarte() }
                    )
                } else {
                    ManoItem(juego.jugadores[0].mano)
                }
            }
        }

    }
}
*/