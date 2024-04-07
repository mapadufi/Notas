package com.example.notas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notas.dao.NotaDao
import com.example.notas.databinding.ActivityEditNotaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditNotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNotaBinding
    private lateinit var notaDao: NotaDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btEditCan.setOnClickListener {
            binding.edtEditTitulo.text.clear()
            binding.edtEditAnotacao.text.clear()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val tituloRecuperado = intent.extras?.getString("titulo")
        val anotacaoRecuperado = intent.extras?.getString("anotacao")
        val uid = intent.extras!!.getInt("uid")

        binding.edtEditTitulo.setText(tituloRecuperado)
        binding.edtEditAnotacao.setText(anotacaoRecuperado)

        binding.btEditSal.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                val titulo = binding.edtEditTitulo.text.toString()
                val anotacao = binding.edtEditAnotacao.text.toString()
                val mensagem: Boolean

                if (titulo.isEmpty() || anotacao.isEmpty()){
                    mensagem = false
                }else{
                    mensagem = true
                    atualizarNota(uid, titulo, anotacao)
                }
                withContext(Dispatchers.Main){
                    if (mensagem){
                        Toast.makeText(this@EditNotaActivity, "Anotação Ataualizada", Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(this@EditNotaActivity, "Preencha todos os campos", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun atualizarNota(uid: Int, titulo: String, anotacao: String){
        notaDao = AppDatabase.getInstance(this).notasDao()
        notaDao.atualizar(uid, titulo, anotacao)
    }
}