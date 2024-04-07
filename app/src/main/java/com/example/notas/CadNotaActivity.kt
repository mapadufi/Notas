package com.example.notas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.notas.dao.NotaDao
import com.example.notas.databinding.ActivityCadNotaBinding
import com.example.notas.model.Nota
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

        binding.btCadCan.setOnClickListener {
            binding.edtCadTitulo.text.clear()
            binding.edtCadAnotacao.text.clear()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


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
                        toastCadastrar()
                        finish()
                    }else{
                        toastErro()
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

    private fun toastCadastrar(){
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.toast_sucesso, findViewById(R.id.container_sucesso))
        val textView = view.findViewById<TextView>(R.id.textoSucesso)
        textView.text = "Cadastrado com sucesso"
        val toastCadastrar = Toast(this)
        toastCadastrar.view = view
        toastCadastrar.duration = Toast.LENGTH_LONG
        toastCadastrar.show()
    }

    private fun toastErro() {
        val inflater = layoutInflater
        val view =
            inflater.inflate(R.layout.toast_customizado_erro, findViewById(R.id.container_erro))
        val toastCadastrar = Toast(this)
        toastCadastrar.view = view
        toastCadastrar.duration = Toast.LENGTH_LONG
        toastCadastrar.show()
    }
}