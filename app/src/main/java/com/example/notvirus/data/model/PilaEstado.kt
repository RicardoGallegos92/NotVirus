package com.example.notvirus.data.model

enum class PilaEstado{
    // está: ->
    VACIO,
    CON_ORGANO,
    INMUNE,
    // se debe: ->
    DEJAR_SOLO_ORGANO,
    DESCARTAR,
    INMUNIZAR,
}