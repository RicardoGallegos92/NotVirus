package com.example.notvirus.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notvirus.ui.screens.ConfiguracionScreen
import com.example.notvirus.ui.screens.ElegirDificultadScreen
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
        startDestination = Jugar
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
                navigateToPvCom = { navController.navigate(ElegirDificultad) },
                navigateToPvP = { navController.navigate(Jugar) }
            )
        }
        composable<ElegirDificultad>{
            ElegirDificultadScreen(
                innerPadding = innerPadding,
                navigateNext = { navController.navigate(Jugar) },
            )
        }
        composable<Jugar> {
            JugarScreen(
                innerPadding = innerPadding,
                navigateToInicio = { navController.navigate(Usuario) },
            )
        }
        composable<Configuracion> {
            ConfiguracionScreen(
                innerPadding = innerPadding,
            )
        }
    }
}
