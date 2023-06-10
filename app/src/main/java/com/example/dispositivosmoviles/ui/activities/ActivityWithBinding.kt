package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R

import com.example.dispositivosmoviles.databinding.ActivityWithBindingBinding
import com.example.dispositivosmoviles.ui.fragments.FirstFragment
import com.google.android.material.snackbar.Snackbar

class ActivityWithBinding : AppCompatActivity() {

    private lateinit var binding : ActivityWithBindingBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UCE", "Entrando a Create")

        binding = ActivityWithBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        var name: String = ""

        // De esta forma accedemos a los contenidos enviados por otra Activity
//        intent.extras.let {
//            // it? significa que este item/objeto puede ser nulo
//            name = it?.getString("var1")!!
//        }
//        Log.d("UCE", "Hola $name")

        binding.textView.text = "Bienvenido $name!"

        /* // Se usa !! si estamos seguros de que siempre llegara informacion a nuestra activity
        intent.extras!!.let {
            // it? significa que este item/objeto puede ser nulo
            var name = it?.getString("var1")

        }
         */

        Log.d("UCE", "Entrando a Start")
        super.onStart()
        initClass()


    }

    override fun onDestroy() {
        super.onDestroy()
    }

    // Debemos invocar a este metodo en la funcion onStart() para que se ejecute
    fun initClass() {
        binding.imageButton2.setOnClickListener {

            // Asi se define un intent y se menciona a que Activity se trasladara
            var intent = Intent(
                this,
                EjercicioPracticoActivity::class.java
            )

            // Con esto iniciamos el otro Activity
            startActivity(intent)

        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    var suma:Int = 0
                    for(i in 1..10){
                        suma += i
                    }
                    Snackbar.make(binding.textView,
                        "La suma es $suma",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(Color.rgb(232, 87, 87)).show()

                    true
                }
                R.id.fav -> {
                    var prod:Int = 1
                    for(i in 1..10){
                        prod *= i
                    }
                    Snackbar.make(binding.textView,
                        "El producto es $prod",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(Color.rgb(29, 167, 242)).show()

                    true
                }
                R.id.chat_gpt -> {
                    val frag = FirstFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    // Reemplaza el contenido
                    transaction.replace(binding.frmContainer.id, frag)

                    // Muestra el fragmento encima del contenido
                    //transaction.add(binding.frmContainer.id, frag)

                    // Para que al presionar el boton hacia atras desaparezca el contenido
                    transaction.addToBackStack(null)

                    transaction.commit()

                    true
                }

                else -> {false}
            }
        }
    }

}