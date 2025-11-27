package com.example.notvirus.data.model

import com.example.notvirus.R

enum class CartaTipo(val iconID: Int) {
    ORGANO(R.drawable.ic_heart),
    VIRUS(R.drawable.ic_virus),
    MEDICINA(R.drawable.ic_pill),
    TRATAMIENTO(R.drawable.ic_cross),
    NULL(R.drawable.ic_null)
}
