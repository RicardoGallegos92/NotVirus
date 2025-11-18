package com.example.notvirus.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.CartaColor
import com.example.notvirus.data.model.CartaIcono
import com.example.notvirus.data.model.CartaImagen
import com.example.notvirus.data.model.CartaTipo

@Preview(showBackground = true)
@Composable
fun CartaItem(
    carta: Carta = Carta(
        tipo = CartaTipo.TRATAMIENTO,
        color = CartaColor.BLANCO,
        icono = CartaIcono.TRATAMIENTO,
        imagen = CartaImagen.TRATAMIENTO_GUANTE_LATEX,
    ),
    anchoCarta: Int = 100,
) {
    Box(
        modifier = Modifier
            .padding(
                vertical = 20.dp,
                horizontal = 10.dp
            )
            .wrapContentSize(),
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Card(
                modifier = Modifier
                    .width(anchoCarta.dp)
                    .aspectRatio(ratio = 0.6f)
                    .border(
                        width = 2.dp,
                        color = Color(0, 0, 0),
                        shape = RoundedCornerShape(10.dp),
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .background(color = carta.color.colorRGB)
                ) {
                    // Arriba
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = carta.icono.vector,
                            tint = Color(0, 0, 0),
                            contentDescription = "icono de la carta",
                        )
                    }
                    // Centro
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(8f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = carta.imagen.id),
                            contentDescription = "Imagen de carta",
                            colorFilter = null,
                        )
                    }
                    // Abajo
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = carta.icono.vector,
                            tint = Color(0, 0, 0),
                            contentDescription = "icono de la carta",
                        )
                    }
                }
            }
        }

    }
}