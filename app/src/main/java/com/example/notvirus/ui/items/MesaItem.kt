package com.example.notvirus.ui.items

import android.R.attr.text
import android.util.Log.i
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.CartaColor
import com.example.notvirus.data.model.CartaIcono
import com.example.notvirus.data.model.CartaImagen
import com.example.notvirus.data.model.CartaTipo
import com.example.notvirus.data.model.Mesa

@Preview
@Composable
fun MesaItem(
    mesa: Mesa = Mesa(),
) {
    Box(
        modifier = Modifier
            .background(Color(0, 0, 0, 128))
            .fillMaxSize()
    ) {
        val pilaSize = mutableListOf(
            mesa.pilaAzul.size,
            mesa.pilaRoja.size,
            mesa.pilaVerde.size,
            mesa.pilaAmarilla.size,
            mesa.pilaMulticolor.size
        )
        val cartasEnMesa = mutableListOf(
            mesa.pilaAzul.lastOrNull() ?: Carta(
                tipo = CartaTipo.NULL,
                color = CartaColor.AZUL,
                icono = CartaIcono.NULL,
                imagen = CartaImagen.NADA,
            ),
            mesa.pilaRoja.lastOrNull() ?: Carta(
                tipo = CartaTipo.NULL,
                color = CartaColor.ROJO,
                icono = CartaIcono.NULL,
                imagen = CartaImagen.NADA,
            ),
            mesa.pilaVerde.lastOrNull() ?: Carta(
                tipo = CartaTipo.NULL,
                color = CartaColor.VERDE,
                icono = CartaIcono.NULL,
                imagen = CartaImagen.NADA,
            ),
            mesa.pilaAmarilla.lastOrNull() ?: Carta(
                tipo = CartaTipo.NULL,
                color = CartaColor.AMARILLO,
                icono = CartaIcono.NULL,
                imagen = CartaImagen.NADA,
            ),
            mesa.pilaMulticolor.lastOrNull() ?: Carta(
                tipo = CartaTipo.NULL,
                color = CartaColor.MULTICOLOR,
                icono = CartaIcono.NULL,
                imagen = CartaImagen.NADA,
            ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            cartasEnMesa.forEach { carta: Carta ->
                Column(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CartaItem(
                        carta = carta,
                        anchoCarta = 70
                    )
                    Text( text = pilaSize[cartasEnMesa.indexOf(carta)].toString() )
                }
            }
        }
    }
}