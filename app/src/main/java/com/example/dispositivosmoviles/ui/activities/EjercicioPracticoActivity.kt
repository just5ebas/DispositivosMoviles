package com.example.dispositivosmoviles.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.databinding.ActivityEjercicioPracticoBinding
import com.example.dispositivosmoviles.ui.data.UserDataStore
import com.example.dispositivosmoviles.ui.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class EjercicioPracticoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEjercicioPracticoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEjercicioPracticoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun initClass() {
        binding.button2.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.txtName.text.toString(),
                binding.txtPassword.text.toString()
            )

            if (check) {

                lifecycleScope.launch(Dispatchers.IO) {
                    saveDataStore(binding.txtName.text.toString())
                }

                var intent = Intent(
                    this,
                    ActivityWithBinding::class.java
                )

                intent.putExtra("var1", binding.txtName.text.toString())

                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.button2,
                    "Usuario o contraseña incorrectos",
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.rgb(232, 87, 87)).show()
            }

        }

        //Intent puedo mandar cualquier cosa
        binding.button5.setOnClickListener{
            //Abre una url con un boton, este intent tiene un punto de partida pero no de llegada
            //con geo: se puede mandar la latitud y longitud de una pos del mapa
          /*  val intent=Intent(
                Intent.ACTION_VIEW,
                Uri.parse("tel:0997125604"))
            */

            //tel:0997125604
            //"geo:-0.1924028,-78.5092874"
            //https://www.google.com.ec

            //va hacer una busqueda en un paquete especifico
            //Los parametros para abrir una aplicacion especifica
            val intent=Intent(Intent.ACTION_WEB_SEARCH)
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            //mandas extras enn los parametros en el intent
            intent.putExtra(SearchManager.QUERY,"UCE fing")
            startActivity(intent)
        }

        val appResultLocal=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultActivity->

            when(resultActivity.resultCode){
                RESULT_OK->{
                   // Log.d("UCE4","Resultado exitoso")
                    Snackbar.make(binding.textView3,"Resultado exitoso",Snackbar.LENGTH_LONG)
                }
                RESULT_CANCELED->{
                    //Log.d("UCE4","Resultado fallido")
                    Snackbar.make(binding.textView10,"Resultado fallido",Snackbar.LENGTH_LONG)
                }
                else->{
                    //Log.d("UCE4","Resultado dudoso")
                    Snackbar.make(binding.textView10,"Resultado dudoso",Snackbar.LENGTH_LONG)
                }
            }
        }
        binding.btnResult.setOnClickListener{
            val resIntent=Intent(this,ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }

    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("email")] = "dimoviles@uce.edu.ec"
            prefs[stringPreferencesKey("password")] = UUID.randomUUID().toString()
        }
    }

}