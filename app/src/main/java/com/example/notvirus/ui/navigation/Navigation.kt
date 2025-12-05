package com.example.notvirus.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.notvirus.data.model.Bot
import com.example.notvirus.ui.screens.ConfiguracionScreen
import com.example.notvirus.ui.screens.ElegirDificultadScreen
import com.example.notvirus.ui.screens.Jugar1PlayerScreen
import com.example.notvirus.ui.screens.Jugar2PlayerScreen
import com.example.notvirus.ui.screens.LoginScreen
import com.example.notvirus.ui.screens.UsuarioScreen

/*
navController.navigateUp()  // <-- volver a pantalla anterior
navController.navigate( "nombre pantalla aquí" ) // <-- ir a una Screen especifica
 */
@Composable
fun Navigation(
    innerPadding: PaddingValues,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Usuario,    // <-- Aquí va el punto de entrada a la app
    ) {
        composable<Login> {
            // llamada a la Screen
            LoginScreen(
                innerPadding = innerPadding,
            )
        }
        composable<Usuario> {
            UsuarioScreen(
                innerPadding = innerPadding,
                navigateToPvCom = { navController.navigate(ElegirDificultad) },
                navigateToPvP = { navController.navigate(Jugar2Player) }
            )
        }
        composable<ElegirDificultad> {
            ElegirDificultadScreen(
                innerPadding = innerPadding,
                navigateBack = { navController.navigateUp() },
                navigateNext = { bot ->
                    navController.navigate(
                        Jugar1Player(
                            bot = bot,
                        )
                    )
                },
            )
        }
        composable<Jugar1Player> { backStackEntry ->
            val data = backStackEntry.toRoute<Jugar1Player>()
            Jugar1PlayerScreen(
                innerPadding = innerPadding,
                navigateToInicio = {  navController.navigate(Usuario) },
                navigateBack = { navController.navigateUp() },
                bot = data.bot
            )
        }
        composable<Jugar2Player> {
            Jugar2PlayerScreen(
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
