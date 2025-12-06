package com.example.notvirus.ui.screens

import android.R.attr.enabled
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notvirus.BuildConfig
import com.example.notvirus.R
import com.example.notvirus.ui.components.BtnSeleccion
import com.example.notvirus.ui.components.MyColumn
import com.example.notvirus.ui.components.VirusGradienteFondo

@Preview
@Composable
fun UsuarioScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navigateToPvCom: () -> Unit = {},
    navigateToPvP: () -> Unit = {},
    navigateBack: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .background(
                brush = VirusGradienteFondo
            )
            .padding(innerPadding)
    )
    Column(
        modifier = Modifier
            .background(color = Color.Transparent)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            modifier = Modifier
                .background(colorResource(R.color.remarcar_texto))
            ,
            text = stringResource(R.string.app_edicion)
        )
        BtnSeleccion(
            onClick = { navigateToPvCom() },
            texto = stringResource(R.string.seleccion_1player),
        )
        BtnSeleccion(
            onClick = { navigateToPvP() },
            texto = stringResource(R.string.seleccion_2player),
        )
        MyColumn(
            textos = listOf(
                "Version: ${BuildConfig.APP_VERSION}",
                stringResource(R.string.app_estado),
            )
        )
    }
}
