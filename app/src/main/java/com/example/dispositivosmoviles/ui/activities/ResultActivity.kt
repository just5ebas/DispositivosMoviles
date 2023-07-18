package com.example.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnOK.setOnClickListener{
            setResult(RESULT_OK)
            //cierra sesion y borra lo que yo tengo archivos temporales
            finish()
        }

        binding.btnFalse.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}