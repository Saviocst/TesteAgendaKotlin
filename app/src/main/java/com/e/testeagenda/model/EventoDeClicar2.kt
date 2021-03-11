package com.e.testeagenda.model

interface EventoDeClicar2 {
    fun aoClicar(id: Int, tipo: String?)
    fun aoClicarSegurar(position: Int, tipo: String?, id: Int)
}