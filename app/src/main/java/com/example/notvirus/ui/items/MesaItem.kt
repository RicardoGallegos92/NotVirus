package com.example.notvirus.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.CartaColor
import com.example.notvirus.data.model.CartaImagen
import com.example.notvirus.data.model.CartaTipo
import com.example.notvirus.data.model.Mesa
import com.example.notvirus.data.model.PilaDeColor

@Preview
@Composable
fun MesaItem(
    mesa: Mesa = Mesa(),
) {
    val cartaDefault: Carta = Carta(
        tipo = CartaTipo.NULL,
        color = CartaColor.NULL,
        imagen = CartaImagen.NADA
    )
    Box(
        modifier = Modifier
            .background(Color(0, 0, 0, 128))
            .fillMaxSize()
    ) {
        Text( text = mesa.turnosParaGanar.toString() )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            mesa.pilas.forEach { pila: PilaDeColor ->
                    Column(
                        modifier = Modifier
                            .wrapContentSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val carta:Carta = pila.cartas.lastOrNull() ?: cartaDefault
//                        println(carta.toString())
                        CartaItem(
                            carta = carta,
                            anchoCarta = 70,
                        )
                        Text( text = pila.cartas.size.toString() )
                    }
            }
        }
    }
}