package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityProgressBinding
import com.example.dispositivosmoviles.ui.viewmodels.ProgressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding

    private val progressViewModel by viewModels<ProgressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Live Data -> Se modifica la UI cuando cambian los data
        progressViewModel.progressState.observe(
            this
        )
        { binding.progressBar.visibility = it }

        progressViewModel.items.observe(
            this
        )
        {
               Toast.makeText(this, it[0].nombre, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, NotificationActivity::class.java))
        }


        // Listeners -> Botones, clics, eventos y acciones que ejecutan procesos.
        binding.btnProceso.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                progressViewModel.progressBackground(3000)
            }
        }

        binding.btnProceso1.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                progressViewModel.getMarvelChars(0, 90)
            }
        }
    }
}
