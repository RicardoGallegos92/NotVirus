package com.example.notvirus.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.CartaColor
import com.example.notvirus.data.model.CartaImagen
import com.example.notvirus.data.model.CartaTipo
import com.example.notvirus.data.model.Mesa
import com.example.notvirus.data.model.PilaDeColor

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
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
//      Text( text = mesa.turnosParaGanar.toString() )
        Row(
            modifier = Modifier
                .fillMaxSize()
            ,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            mesa.pilas.forEach { pila: PilaDeColor ->
                    val carta:Carta? = pila.cartas.lastOrNull()
//                  println(carta.toString())
                    if( carta != null ){
                        CartaItem(
                            carta = carta,
                            anchoCarta = 70,
                        )
//                  Text( text = pila.cartas.size.toString() )
                    }
            }
        }
    }
}