package com.example.notvirus.data.model

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import java.util.UUID

data class Carta(
    val tipo: CartaTipo,
    val color: CartaColor,
    val icono: CartaIcono,
    val imagen: CartaImagen,
    var seleccionada: Boolean = false,
    val id: String = UUID.randomUUID().toString(),
){
/*
    fun clicked(){
        seleccionada != seleccionada
    }
*/

    @Composable
    fun rotateCarta(rotar: Boolean) {
        // animacion
        val rotation by animateFloatAsState(
            targetValue = if (rotar) 360f else 0f,
            animationSpec = tween(durationMillis = 1000),
            label = "Rotación"
        )

        Text(
            text = "Rotando...",
            modifier = Modifier.rotate(rotation)
        )
    }
}