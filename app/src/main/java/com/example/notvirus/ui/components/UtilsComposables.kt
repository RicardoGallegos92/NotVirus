package com.example.notvirus.ui.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notvirus.R
import com.example.notvirus.data.model.Juego
import com.example.notvirus.data.model.Jugador
import com.example.notvirus.ui.items.ManoItem
import com.example.notvirus.ui.items.MesaItem
import com.example.notvirus.ui.viewModels.JugarUiState
import com.example.notvirus.ui.viewModels.JugarViewModel

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
    modifier: Modifier = Modifier,
    onClick:() -> Unit = {},
    texto:String = "testText",
    enabled :Boolean = true
){
    Button(
        modifier = modifier
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


@Composable
fun MensajeError(error: String) {
    MyColumn(listOf(error))
}

@Composable
fun ZonaJugadorArriba(
    jugador: Jugador,
    viewModel: JugarViewModel,
    activeBtns:Boolean,
    activeBtnPlayCard: Boolean,
    activeBtnDiscardCards: Boolean,
) {
    Column(
        modifier = Modifier
            .background(color = Color(3, 70, 148))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Row(
            // Jugador CPU - Mano
            modifier = Modifier
                .background(color = Color(3, 70, 148))
                .fillMaxWidth()
                .weight(1f)
        ) {
//            ManoItemEnemy()
//            /*
            ManoItem(
                mano = jugador.mano,
                viewModel = viewModel,
                useButtons = activeBtns,
                activeBtnPlayCard = activeBtnPlayCard,
                activeBtnDiscardCards = activeBtnDiscardCards,
            )
//            */
        }
        Row(
            // Jugador CPU - Mesa
            modifier = Modifier
                .background(color = Color(3, 70, 148))
                .fillMaxWidth()
                .weight(1f)
        ) {
            MesaItem(mesa = jugador.mesa)
        }
    }
}

@Composable
fun ZonaCentral(
    juego: Juego,
    juegoViewModel: JugarViewModel,
) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        // Baraja
        PilaDeCartasItem(
            texto = "Baraja",
            cantidadCartas = juego.getBarajaSize()
        )
        Log.i("ZonaCentral","Baraja: ${juego.baraja.pila.size}")
        // boton de Pausa
        BtnSeleccion(
            texto = stringResource(R.string.btn_pausa),
            onClick = { juegoViewModel.pauseJuego() },
        )
        // Pila descarte
        PilaDeCartasItem(
            texto = "Descarte",
            cantidadCartas = juego.getDescarteSize(),
        )
        Log.i("ZonaCentral","Descarte: ${juego.pilaDescarte.pila.size}")
    }
}

@Composable
fun ZonaJugadorAbajo(
    jugador: Jugador,
    viewModel: JugarViewModel,
    uiState: JugarUiState,
    activeBtns:Boolean,
    activeBtnPlayCard: Boolean,
    activeBtnDiscardCards: Boolean,
) {
    Column(
        modifier = Modifier
            .background(color = Color.Transparent)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Row(
            // Jugador Humano - Mesa
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            MesaItem(mesa = jugador.mesa)
        }
        Row(
            // Jugador Humano - Mano
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .padding(0.dp)
                .weight(1f),
        ) {
            ManoItem(
                mano = jugador.mano,
                viewModel = viewModel,
                useButtons = activeBtns,
                activeBtnPlayCard = activeBtnPlayCard,
                activeBtnDiscardCards = activeBtnDiscardCards,
            )
        }
    }
}

@Composable
fun PilaDeCartasItem(
    texto: String = "Nombre Pila",
    cantidadCartas: Int = 30,
) {
    val ANCHO_PILA: Int = 100
    val ALTO_CARTA = 2
    val ALTO_PILA: Int = (68 * ALTO_CARTA)
    val FUENTE_NUMERO: Int = 20
    val FUENTE_TEXTO: Int = 18
    Column(
        // Cajón
        modifier = Modifier
            .background(color = Color.Transparent)
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = texto,
            color = colorResource(R.color.black),
            fontSize = FUENTE_TEXTO.sp,
        )
        Column(
            // tamaño total en Negativo
            modifier = Modifier
                .background(color = colorResource(R.color.black))
                .width(ANCHO_PILA.dp)
                .height(ALTO_PILA.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                // representacion de cartas contenidas en color carta
                modifier = Modifier
                    .background(color = colorResource(R.color.white))
                    .fillMaxWidth()
                    .height((cantidadCartas * ALTO_CARTA).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) { }
        }
        Text(
            text = cantidadCartas.toString(),
            color = Color(0, 0, 0),
            fontSize = FUENTE_NUMERO.sp,
        )
    }
}

@Composable
fun MenuPausa(
    actionBack: () -> Unit = {},
    actionContinuar: () -> Unit = { },
    actionReiniciar: () -> Unit = { },
    actionSalir: () -> Unit = {},
    onePlayer: Boolean = false,
) {
    // boton de UnPause
    BtnSeleccion(
        onClick = { actionContinuar() },
        texto = stringResource(R.string.juego_continuar),
    )
    BtnSeleccion(
        onClick = { actionReiniciar() },
        texto = stringResource(R.string.juego_reiniciar),
    )
    if (onePlayer){
        BtnSeleccion(
            onClick = { actionBack() },
            texto = stringResource(R.string.juego_cambiarBot)
        )
    }
    BtnSeleccion(
        onClick = { actionSalir() },
        texto = stringResource(R.string.btn_salir),
    )
}

@Composable
fun MenuJuegoTerminado(
    nombreGanador: String,
    navigateToInicio: () -> Unit,
    actionBack: () -> Unit = {},
    revancha: () -> Unit,
    onePlayer: Boolean = false,
) {
    MyColumn(
        textos = listOf(
            "Ganador: $nombreGanador",
            stringResource(R.string.juego_ganador),
        )
    )
    BtnSeleccion(
        onClick = { revancha() },
        texto = stringResource(R.string.juego_iniciar),
    )
    if (onePlayer){
        BtnSeleccion(
            onClick = { actionBack() },
            texto = stringResource(R.string.juego_cambiarBot)
        )
    }
    BtnSeleccion(
        onClick = { navigateToInicio() },
        texto = stringResource(R.string.btn_salir),
    )
}