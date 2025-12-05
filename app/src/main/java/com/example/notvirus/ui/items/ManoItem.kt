package com.example.notvirus.ui.items

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.Mano
import com.example.notvirus.ui.components.BtnMano
import com.example.notvirus.ui.viewModels.JugarViewModel

const val TAG: String = "ManoItem"

@Preview
@Composable
fun ManoItem(
    mano: Mano = Mano(),
    viewModel: JugarViewModel? = null,
    useButtons: Boolean = false,
    activeBtnPlayCard: Boolean = false,
    activeBtnDiscardCards: Boolean = false,
) {
    Row(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .padding(0.dp),
    ) {
        if (useButtons) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                BtnMano(
                    enabled = activeBtnDiscardCards,
                    onClick = {
                        Log.i(TAG, "btn discard presionado")
                        viewModel?.descartarCartas()
                    },
                    texto = "Descartar",
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(5f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxSize()
                    .padding(0.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(mano.cartas) { carta: Carta ->
                        CartaItem(
                            carta = carta,
                            //seleccionada = selectedCartas[index],
                            onClick = {
                                viewModel?.clickedCard(carta.id)
                            }
                        )
                    }
                }
            }
        }
        if (useButtons) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                BtnMano(
                    enabled = activeBtnPlayCard,
                    onClick = {
                        Log.i(TAG, "btn jugar presionado")
                        viewModel?.jugarCarta()
                    },
                    texto = "Jugar",
                )
            }
        }
    }
}


