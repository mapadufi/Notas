package com.example.notas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notas.dao.NotaDao
import com.example.notas.databinding.ActivityCadNotaBinding
import com.example.notas.model.Nota
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadNotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadNotaBinding
    private lateinit var notaDao: NotaDao
    private val listaNotas: MutableList<Nota> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)


            binding.btCadSal.setOnClickListener{

                CoroutineScope(Dispatchers.IO).launch {

                val titulo = binding.edtCadTitulo.text.toString()
                val anotacao = binding.edtCadAnotacao.text.toString()
                val mensagem: Boolean

                if (titulo.isEmpty() || anotacao.isEmpty()){
                    mensagem = false
                }else {
                    mensagem = true
                    cadastrarNota(titulo, anotacao)
                }

                withContext(Dispatchers.Main){
                    if (mensagem){
                        Toast.makeText(applicationContext, "Anotação Salva", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext, "Preencha todos os campos", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun cadastrarNota(titulo: String, anotacao: String){
        val nota = Nota(titulo, anotacao)
        listaNotas.add(nota)
        notaDao = AppDatabase.getInstance(this).notasDao()
        notaDao.inserir(listaNotas)


    }
}