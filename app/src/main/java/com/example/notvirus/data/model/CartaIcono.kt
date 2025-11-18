package com.example.notvirus.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.notvirus.R

enum class CartaIcono(val vector: ImageVector) {
    CORAZON(Icons.Rounded.Favorite),
    ESTOMAGO(Icons.Rounded.Info),
    CEREBRO(Icons.Rounded.Face),
    HUESO(Icons.Rounded.Create),
    CUERPO(Icons.Rounded.Person),
    VIRUS(Icons.Rounded.Close),
    MEDICINA(Icons.Rounded.ThumbUp),
    TRATAMIENTO(Icons.Rounded.Add),
    NULL(ImageVectorEmpty)
}

val ImageVectorEmpty: ImageVector
    get() = ImageVector.Builder(
        name = "Empty",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).build()