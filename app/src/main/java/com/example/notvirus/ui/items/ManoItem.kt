package com.example.notvirus.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.Mano
import com.example.notvirus.ui.viewModels.JugarViewModel

@Composable
fun ManoItem(
    mano: Mano = Mano(),
    viewModel: JugarViewModel,
    useButtons: Boolean = false,
    activeBtnPlayCard: Boolean,
    activeBtnDiscardCards: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            if (useButtons) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        enabled = activeBtnPlayCard,
                        onClick = {
                            viewModel.jugarCarta()
                        }
                    ) { Text(text = "Jugar") }
                    Button(
                        enabled = activeBtnDiscardCards,
                        onClick = {
                            println("btn discard presionado")
                            viewModel.descartarCartas()
                        }
                    ) { Text(text = "Descartar") }
                }
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(mano.cartas) { carta: Carta ->
                    val index = mano.cartas.indexOf(carta)
                    CartaItem(
                        carta = carta,
                        //seleccionada = selectedCartas[index],
                        onClick = {
                            viewModel.clickedCard(index)
                        }
                    )
                }
            }
        }
    }
}
