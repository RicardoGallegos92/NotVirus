package com.example.notvirus.data.model

import com.example.notvirus.R

enum class CartaImagen(val id: Int) {
// ORGANOS
    CORAZON(R.drawable.corazon),
    ESTOMAGO(R.drawable.estomago),
    CEREBRO(R.drawable.cerebro),
    HUESO(R.drawable.hueso),
    CUERPO(R.drawable.cuerpo),

// VIRUS
    VIRUS_ROJO(R.drawable.virus_rojo),
    VIRUS_AMARILLO(R.drawable.virus_amarillo),
    VIRUS_VERDE(R.drawable.virus_verde),
    VIRUS_AZUL(R.drawable.virus_azul),
    VIRUS_MULTICOLOR(R.drawable.virus_multicolor),

// MEDICINAS
    MEDICINA_ROJO(R.drawable.medicina_rojo),
    MEDICINA_AMARILLO(R.drawable.medicina_amarillo),
    MEDICINA_VERDE(R.drawable.medicina_verde),
    MEDICINA_AZUL(R.drawable.medicina_azul),
    MEDICINA_MULTICOLOR(R.drawable.medicina_multicolor),

// TRATAMIENTOS
    TRATAMIENTO_CONTAGIO(R.drawable.tratamiento_contagio),
    TRATAMIENTO_ERROR_MEDICO(R.drawable.tratamiento_error_medico),
    TRATAMIENTO_GUANTE_LATEX(R.drawable.tratamiento_guante_latex),
    TRATAMIENTO_ROBO_ORGANO(R.drawable.tratamiento_robo_organo),
    TRATAMIENTO_TRANSPLANTE(R.drawable.tratamiento_transplante),
// VACIO
    NADA(R.drawable.nada)
}
