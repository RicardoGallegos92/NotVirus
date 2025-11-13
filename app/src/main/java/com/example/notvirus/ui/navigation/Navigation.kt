package com.example.notvirus.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notvirus.ui.screens.ConfiguracionScreen
import com.example.notvirus.ui.screens.JugarScreen
import com.example.notvirus.ui.screens.LoginScreen

@Composable
fun Navigation(
    innerPadding: PaddingValues
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Jugar
    ) {
        composable<Login> {
            //llamada a la screen
            LoginScreen(
                innerPadding = innerPadding,
            )
        }
        composable<Jugar> {
            //llamada a la screen
            JugarScreen(
                innerPadding = innerPadding,
            )
        }
        composable<Configuracion> {
            //llamada a la screen
            ConfiguracionScreen(
                innerPadding = innerPadding,
            )
        }
    }
}