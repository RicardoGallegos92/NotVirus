package com.example.notvirus.ui.navigation

import com.example.notvirus.data.model.Bot
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Usuario

@Serializable
object ElegirDificultad

@Serializable
data class Jugar1Player(
    val bot: Bot,
)

@Serializable
object Jugar2Player

@Serializable
object Configuracion