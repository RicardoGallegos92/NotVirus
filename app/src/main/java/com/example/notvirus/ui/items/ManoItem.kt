package com.example.notvirus.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.CartaColor
import com.example.notvirus.data.model.CartaImagen
import com.example.notvirus.data.model.CartaTipo

@Preview(showBackground = true)
@Composable
fun ManoItem(
    cartasMano: MutableList<Carta> = mutableListOf(
        Carta(CartaTipo.ORGANO, CartaColor.VERDE, cartaImagen =  CartaImagen.CORAZON),
        Carta(tipo = CartaTipo.TRATAMIENTO, color = CartaColor.BLANCO, cartaImagen = CartaImagen.TRATAMIENTO),
        Carta(tipo = CartaTipo.VIRUS, color = CartaColor.ROJO,cartaImagen =  CartaImagen.VIRUS),
    ),
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        ,
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
            ,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            items(cartasMano){ carta:Carta ->
                CartaItem(carta = carta)
            }
        }

    }
}
