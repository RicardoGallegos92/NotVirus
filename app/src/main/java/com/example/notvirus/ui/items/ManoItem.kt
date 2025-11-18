package com.example.notvirus.ui.items

import android.R.attr.onClick
import android.R.attr.text
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notvirus.data.model.Carta
import com.example.notvirus.data.model.CartaColor
import com.example.notvirus.data.model.CartaIcono
import com.example.notvirus.data.model.CartaImagen
import com.example.notvirus.data.model.CartaTipo
import com.example.notvirus.data.model.Mano
import com.example.notvirus.ui.viewModels.ManoViewModel

@Preview(showBackground = true)
@Composable
fun ManoItem(
    mano: Mano = Mano(),
    play: () -> Unit = {},
    discard: () -> Unit = {},
    manoViewModel: ManoViewModel = viewModel(),
    useButtons: Boolean = false,
    ) {
    val uiState by manoViewModel.uiState.collectAsStateWithLifecycle()

    val selectedCartas = uiState.selectedCartas

    val activeBtnPlayCard = uiState.activeBtnPlayCard

    val activeBtnDiscardCards = uiState.activeBtnDiscardCards

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
                        onClick = { play() }
                    ) { Text(text = "Jugar") }
                    Button(
                        enabled = activeBtnDiscardCards,
                        onClick = {
                            println("btn discard presionado")
                            discard()
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
//                    carta.seleccionada = selectedCartas[index]
                    CartaItem(
                        carta = carta,
                        seleccionada = selectedCartas[index],
                        onClick = {
                            manoViewModel.clickedCard(index)
                        }
                    )
                }
            }
        }
    }
}
