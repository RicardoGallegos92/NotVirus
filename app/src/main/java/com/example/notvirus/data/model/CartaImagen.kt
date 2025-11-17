package com.example.notvirus.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

enum class CartaImagen(val linkImagen: ImageVector) {
    CORAZON(Icons.Rounded.Favorite),
    ESTOMAGO(Icons.Rounded.Info),
    CEREBRO(Icons.Rounded.Face),
    HUESO(Icons.Rounded.Create),
    CUERPO(Icons.Rounded.Person),
    VIRUS(Icons.Rounded.Close),
    MEDICINA(Icons.Rounded.ThumbUp),
    TRATAMIENTO(Icons.Rounded.Add),
}
