package com.example.notvirus.pruebas

class Prueba() {
    public fun sumaListas() {
        val lista1: List<Int> = listOf(1, 2, 3)
        val lista2: List<Int> = listOf(4, 5)

        val lista12 = lista1 + lista2
        println("Lista 1+2 = $lista12")

        val lista21 = lista2 + lista1
        println("Lista 2+1 = $lista21")
    }

}
    fun main() {
        val test = Prueba()

        test.sumaListas()
    }
