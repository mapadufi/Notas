package com.example.notas

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notas.adapter.NotaAdapter
import com.example.notas.dao.NotaDao
import com.example.notas.databinding.ActivityMainBinding
import com.example.notas.model.Nota
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notaDao: NotaDao
    private lateinit var notaAdapter: NotaAdapter
    private val _listaNotas = MutableLiveData<MutableList<Nota>>()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            getNotas()

            withContext(Dispatchers.Main){

                _listaNotas.observe(this@MainActivity){listaNotas ->
                    val recyclerViewNotas = binding.recyclerNota
                    recyclerViewNotas.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewNotas.setHasFixedSize(true)
                    notaAdapter = NotaAdapter(this@MainActivity, listaNotas)
                    recyclerViewNotas.adapter = notaAdapter
                    notaAdapter.notifyDataSetChanged()
                }
            }
        }


        binding.btAdd.setOnClickListener{
            val cadNota = Intent(this, CadNotaActivity::class.java)
            startActivity(cadNota)
        }
    }


    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            getNotas()

            withContext(Dispatchers.Main){

                _listaNotas.observe(this@MainActivity){listaNotas ->
                    val recyclerViewNotas = binding.recyclerNota
                    recyclerViewNotas.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewNotas.setHasFixedSize(true)
                    notaAdapter = NotaAdapter(this@MainActivity, listaNotas)
                    recyclerViewNotas.adapter = notaAdapter
                    notaAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getNotas(){
        notaDao = AppDatabase.getInstance(this).notasDao()
        val listaNotas: MutableList<Nota> = notaDao.get()
        _listaNotas.postValue(listaNotas)


    }
}