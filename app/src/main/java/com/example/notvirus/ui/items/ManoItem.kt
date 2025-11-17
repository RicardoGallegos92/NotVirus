package com.example.notvirus.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.CartaColor
import com.example.notvirus.data.model.CartaIcono
import com.example.notvirus.data.model.CartaImagen
import com.example.notvirus.data.model.CartaTipo
import com.example.notvirus.data.model.Mano

@Preview(showBackground = true)
@Composable
fun ManoItem(
    mano: Mano = Mano()
/*
    cartasMano: MutableList<Carta> = mutableListOf(
        Carta(CartaTipo.ORGANO, CartaColor.ROJO,imagen =  CartaImagen.CORAZON, icono = CartaIcono.CORAZON),
        Carta(tipo = CartaTipo.TRATAMIENTO, color = CartaColor.BLANCO, imagen = CartaImagen.TRATAMIENTO_CONTAGIO , icono = CartaIcono.TRATAMIENTO),
        Carta(tipo = CartaTipo.VIRUS, color = CartaColor.VERDE, imagen = CartaImagen.VIRUS_VERDE, icono = CartaIcono.VIRUS),
    )
*/
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
            items(mano.cartas){ carta:Carta ->
                CartaItem(carta = carta)
            }
        }

    }
}
