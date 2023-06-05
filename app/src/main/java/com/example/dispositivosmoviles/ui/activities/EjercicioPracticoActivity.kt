package com.example.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.databinding.ActivityEjercicioPracticoBinding

class EjercicioPracticoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEjercicioPracticoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEjercicioPracticoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}