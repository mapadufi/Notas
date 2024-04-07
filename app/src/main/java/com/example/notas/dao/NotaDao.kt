package com.example.notas.dao

import androidx.room.Insert
import com.example.notas.model.Nota

interface NotaDao {

    @Insert
    fun inserir(listaNotas: MutableList<Nota>)
}