package com.example.notvirus.ui.screens

import android.R.attr.onClick
import android.text.Layout
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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notvirus.R
import com.example.notvirus.data.model.Bot
import com.example.notvirus.ui.components.BtnSeleccion
import com.example.notvirus.ui.components.VirusGradienteFondo

@Preview(showBackground = true)
@Composable
fun ElegirDificultadScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navigateNext: (Bot) -> Unit = {},
    navigateBack: () -> Unit = {},
){
    Box(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .background(
                brush = VirusGradienteFondo
            )
            .padding(innerPadding)
    ){
        BtnSeleccion(
            modifier = Modifier.align(alignment = Alignment.BottomEnd),
            onClick = { navigateBack() },
            texto = stringResource(R.string.to_pantalla_Inicio)
        )
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.Transparent)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ){
            BtnSeleccion(
                onClick = { navigateNext(Bot.COPO) },
                texto = stringResource(R.string.seleccion_facil),
            )
            BtnSeleccion(
                onClick = { navigateNext(Bot.NICO) },
                texto = stringResource(R.string.seleccion_medio),
                enabled = false,
            )
            BtnSeleccion(
                onClick = { navigateNext(Bot.MANU) },
                texto = stringResource(R.string.seleccion_dificil),
                enabled = false,
            )
            BtnSeleccion(
                onClick = { navigateNext(Bot.SANSON) },
                texto = stringResource(R.string.seleccion_competitivo),
                enabled = false,
            )

            BtnSeleccion(
                onClick = { navigateNext(Bot.FER) },
                texto = stringResource(R.string.seleccion_tutorial),
                enabled = false,
            )
        }
    }
}
