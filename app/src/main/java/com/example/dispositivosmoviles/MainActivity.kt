package com.example.dispositivosmoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button2 = findViewById<Button>(R.id.button2)
        var txtBuscar = findViewById<TextView>(R.id.txt_buscar)

        button2.text = "INGRESAR"
        button2.setOnClickListener{
            txtBuscar.text = "El evento se ha ejecutado"

            Toast.makeText(this, "AM MC xdxdxd",
                Toast.LENGTH_SHORT
            ).show()

            Snackbar.make(
                button2,
                "Este es otro mensaje",
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }
}