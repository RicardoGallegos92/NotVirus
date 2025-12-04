package com.example.notvirus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notvirus.R
import com.example.notvirus.ui.components.BtnSeleccion
import com.example.notvirus.ui.components.VirusGradienteFondo

@Preview(showBackground = true)
@Composable
fun ElegirDificultadScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navigateNext: () -> Unit = {},
    navigateBack: () -> Unit = {},
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = VirusGradienteFondo
            )
    ){
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.Transparent)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ){
            BtnSeleccion(
                onClick = { navigateNext() },
                texto = stringResource(R.string.seleccion_facil),
            )
            BtnSeleccion(
                onClick = { navigateNext() },
                texto = stringResource(R.string.seleccion_medio),
            )
            BtnSeleccion(
                onClick = { navigateNext() },
                texto = stringResource(R.string.seleccion_dificil),
            )
            BtnSeleccion(
                onClick = { navigateNext() },
                texto = stringResource(R.string.seleccion_competitivo),
            )

            BtnSeleccion(
                onClick = { navigateNext() },
                texto = stringResource(R.string.seleccion_tutorial),
            )
        }
    }
}
