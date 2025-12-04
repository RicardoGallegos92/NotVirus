package com.example.notvirus.ui.components

import android.R.attr.enabled
import android.R.attr.text
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notvirus.R

val VirusGradienteFondo: Brush
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
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BtnSeleccion(
    onClick:() -> Unit = {},
    texto:String = "testText",
    enabled :Boolean = true
){
    Button(
        modifier = Modifier
            .height(50.dp)
            .wrapContentWidth()
            .padding(horizontal = 10.dp)
            .border(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(R.color.virus_verde_oscuro)
                ),
            )
        ,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        )
        ,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.remarcar_texto), // Color de fondo
            contentColor = Color.White // Color del texto
        ),
        onClick = { onClick() },
        content = { Text(
            text = texto
        ) },
        enabled = enabled
    )
}

@Composable
fun BtnMano(
    enabled: Boolean = false,
    onClick: () -> Unit = {},
    texto: String = "SampleText",
) {
    var a: String = ""
    texto.forEach { char: Char ->
        a += char + "\n"
    }
    Button(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxHeight()
            .padding(0.dp)
            .wrapContentWidth()
            .border(
                shape = RectangleShape,
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(R.color.virus_verde_oscuro)
                ),
            ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        ),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.remarcar_texto), // Color de fondo
            contentColor = Color.White // Color del texto
        ),
        enabled = enabled,
        onClick = { onClick() },
        content = {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = a,
            )
        },
    )
}