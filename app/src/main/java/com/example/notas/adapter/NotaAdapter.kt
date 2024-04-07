package com.example.notas.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notas.EditNotaActivity
import com.example.notas.databinding.NotaItemBinding
import com.example.notas.model.Nota

class NotaAdapter(private val context: Context, private val listaNotas: MutableList<Nota>):
    RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val itemLista = NotaItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return NotaViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        holder.txtNotaTitulo.text = listaNotas[position].titulo
        holder.txtNotaAnotacao.text = listaNotas[position].anotacao

        holder.btNotaEdit.setOnClickListener{
            val intent = Intent(context, EditNotaActivity::class.java)
            intent.putExtra("titulo", listaNotas[position].titulo)
            intent.putExtra("anotacao", listaNotas[position].anotacao)
            intent.putExtra("uid", listaNotas[position].uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = listaNotas.size

    inner class NotaViewHolder(binding: NotaItemBinding) : RecyclerView.ViewHolder(binding.root){
        val txtNotaTitulo = binding.txtNotaTitulo
        val txtNotaAnotacao = binding.txtNotaAnotacao
        val btNotaEdit = binding.btNotaEdit
        val btNotaDel = binding.btNotaDel

    }

}