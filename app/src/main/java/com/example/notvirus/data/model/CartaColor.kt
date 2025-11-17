package com.example.notvirus.data.model

import androidx.compose.ui.graphics.Color


enum class CartaColor(val colorRGB: Color) {
    BLANCO(Color(255, 255, 255)),
    ROJO(Color(255, 0, 0)),
    AZUL(Color(0, 0, 255)),
    AMARILLO(Color(255, 255, 0)),
    VERDE(Color(0, 255, 0)),
    MULTICOLOR(Color(0, 0, 0)),
}