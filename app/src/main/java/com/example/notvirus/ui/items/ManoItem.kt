package com.example.notvirus.ui.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.CartaColor
import com.example.notvirus.data.model.CartaTipo

@Preview(showBackground = true)
@Composable
fun ManoItem(
    cartas: MutableList<Carta> = mutableListOf(
        Carta(
            tipo = CartaTipo.ORGANO,
            color = CartaColor.VERDE
        ),
    ),
) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .aspectRatio(ratio = 0.6f),
    ) {

    }
}
