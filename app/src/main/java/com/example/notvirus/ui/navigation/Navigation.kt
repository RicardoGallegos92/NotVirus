package com.example.notvirus.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notvirus.ui.screens.ConfiguracionScreen
import com.example.notvirus.ui.screens.JugarScreen
import com.example.notvirus.ui.screens.LoginScreen
import com.example.notvirus.ui.screens.UsuarioScreen

/*
navController.navigateUp()  // <-- volver a pantalla anterior
navController.navigate( "nombre pantalla aquí" ) // <-- ir a una Screen especifica
 */
@Composable
fun Navigation(
    innerPadding: PaddingValues
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Usuario
    ) {
        composable<Login> {
            // llamada a la Screen
            LoginScreen(
                innerPadding = innerPadding,
            )
        }
        composable<Usuario>{
            UsuarioScreen(
                innerPadding = innerPadding,
                navigateToJuego = { navController.navigate(Jugar) },
            )
        }
        composable<Jugar> {
            JugarScreen(
                innerPadding = innerPadding,
                onNavigateBack = { navController.navigateUp() },
            )
        }
        composable<Configuracion> {
            ConfiguracionScreen(
                innerPadding = innerPadding,
            )
        }
    }
}