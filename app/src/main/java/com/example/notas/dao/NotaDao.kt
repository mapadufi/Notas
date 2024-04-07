package com.example.notas.dao

import androidx.room.Insert
import androidx.room.Query
import com.example.notas.model.Nota

interface NotaDao {

    @Insert
    fun inserir(listaNotas: MutableList<Nota>)

    @Query("SELECT * FROM tabela_notas ORDER BY uid ASC")
    fun get(): MutableList<Nota>

}