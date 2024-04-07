package com.example.notas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notas.databinding.ActivityCadNotaBinding

class CadNotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadNotaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}