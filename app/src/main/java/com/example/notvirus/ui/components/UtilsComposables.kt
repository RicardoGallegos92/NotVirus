package com.example.notvirus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.notvirus.R

val pizzaGradiente: Brush
    @Composable
    get() = Brush.sweepGradient( // Gradiente lineal
    colors = listOf(
        colorResource(R.color.virus_verde_claro),
        colorResource(R.color.virus_verde_oscuro),
        colorResource(R.color.virus_verde_claro),
        colorResource(R.color.virus_verde_oscuro),
        colorResource(R.color.virus_verde_claro),
        colorResource(R.color.virus_verde_oscuro),
        colorResource(R.color.virus_verde_claro),
        colorResource(R.color.virus_verde_oscuro),
        colorResource(R.color.virus_verde_claro),
        colorResource(R.color.virus_verde_oscuro),
        colorResource(R.color.virus_verde_claro),
        colorResource(R.color.virus_verde_oscuro),
        colorResource(R.color.virus_verde_claro),
        colorResource(R.color.virus_verde_oscuro),
        colorResource(R.color.virus_verde_claro),
        colorResource(R.color.virus_verde_oscuro),
        colorResource(R.color.virus_verde_claro),
    )
)

@Preview
@Composable
fun MyColumn(textos: List<String> = listOf("Texto1", "Texto2", "Texto3", "Texto4", "Texto5")) {
    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        textos.forEach { texto: String ->
            Text(
                modifier = Modifier
                    //.background(color = Color(0,0,0,50))
                    .background(colorResource(R.color.remarcar_texto))
                ,
                text = "${texto}"
            )
        }
    }
}