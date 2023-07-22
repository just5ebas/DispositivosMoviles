package com.example.dispositivosmoviles.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityEjercicioPracticoBinding
import com.example.dispositivosmoviles.ui.validator.LoginValidator
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class EjercicioPracticoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEjercicioPracticoBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEjercicioPracticoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
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
        binding.micro.setOnClickListener {
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
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            //mandas extras enn los parametros en el intent
            intent.putExtra(SearchManager.QUERY, "UCE fing")
            startActivity(intent)
        }

        val appResultLocal =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultActivity ->

                val sn = Snackbar.make(binding.micro, "", Snackbar.LENGTH_LONG)

                var message = when (resultActivity.resultCode) {
                    RESULT_OK -> {
                        sn.setBackgroundTint(resources.getColor(R.color.azul))
                        resultActivity.data?.getStringExtra("result").orEmpty()
                    }

                    RESULT_CANCELED -> {
                        sn.setBackgroundTint(resources.getColor(R.color.rojo_pasion))
                        resultActivity.data?.getStringExtra("result").orEmpty()
                    }

                    else -> {
                        "Resultado Erroneo"
                    }
//
                }
                sn.setText(message)
                sn.show()
            }


        val speechToText =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                val sn = Snackbar.make(
                    binding.root,
                    "",
                    Snackbar.LENGTH_LONG
                )

                var message = ""
                when (activityResult.resultCode) {
                    RESULT_OK -> {
                        val msg =
                            activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                                .toString()
                        if (msg.isNotEmpty()) {
                            val intent = Intent(
                                Intent.ACTION_WEB_SEARCH
                            )
                            intent.setClassName(
                                "com.google.android.googlequicksearchbox",
                                "com.google.android.googlequicksearchbox.SearchActivity"
                            )
                            intent.putExtra(SearchManager.QUERY, msg.toString())
                            startActivity(intent)
                        }
                    }


                    RESULT_CANCELED -> {
                        message = "Proceso Cancelado"
                        sn.setBackgroundTint(resources.getColor(R.color.rojo_pasion))
                    }

                    else -> {
                        message = "Resultado Erroneo"
                        sn.setBackgroundTint(resources.getColor(R.color.rojo_pasion))
                    }

                }
                sn.setText(message)
                sn.show()

            }

        //Diferencia con la primera es que las 2 se van a comunicar y cuando lo hagan se va alanzar este contrato
        binding.btnResult.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }

        val locationContract =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                when (isGranted) {
                    true -> {

                        val task = fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                            val a = Geocoder(this)
                            a.getFromLocation(it.latitude, it.longitude, 1)
                        }

//                        task.addOnSuccessListener {
//                            if (task.result != null) {
//                                Snackbar.make(
//                                    binding.textView3,
//                                    "${it.latitude}, ${it.longitude}",
//                                    Snackbar.LENGTH_LONG
//                                ).show()
//                            } else {
//                                Snackbar.make(
//                                    binding.textView3,
//                                    "Encienda el GPS por favor",
//                                    Snackbar.LENGTH_LONG
//                                ).show()
//                            }
//                        }

                    }

                    shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) -> {
                        Snackbar.make(
                            binding.textView3,
                            "Ayude con el permiso por fa",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                    false -> {
                        Snackbar.make(
                            binding.textView3,
                            "Permiso denegado",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }


        binding.textView10.setOnClickListener {
            locationContract.launch(Manifest.permission.ACCESS_FINE_LOCATION)


//            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//            intentSpeech.putExtra(
//                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//            )
//            intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo")
//            speechToText.launch(intentSpeech)
        }

        binding.button5.setOnClickListener {
            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo")
            speechToText.launch(intentSpeech)
        }


        binding.btnResult.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
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