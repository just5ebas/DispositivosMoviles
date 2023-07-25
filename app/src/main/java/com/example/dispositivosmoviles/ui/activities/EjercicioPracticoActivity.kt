package com.example.dispositivosmoviles.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class EjercicioPracticoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEjercicioPracticoBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBack: LocationCallback

    private var currentLocation: Location? = null

    private val speechToText =
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

    @SuppressLint("MissingPermission")
    private val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {
                true -> {
                    val task = fusedLocationProviderClient.lastLocation

                    task.addOnSuccessListener { location ->

                        val alert = AlertDialog.Builder(this) // Podemos poner estilos junto al this (this, androidx.appcompat.R.style.Platform_ThemeOverlay_AppCompat_Dark)
                        alert.apply {
                            setTitle("ALERTA")
                            setMessage("Existe un problema de posicionamiento global en el sistema")
                            setPositiveButton("OK") { dialog, id ->
                                dialog.dismiss()
                            }
                            setNegativeButton("NO") { dialog, id ->
                                dialog.dismiss()
                            }
                            setCancelable(false) // Para que unicamente se abandone el mensaje si se presiona el boton
                        }.create()
                        alert.show()

                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            locationCallBack,
                            Looper.getMainLooper()
                        )
                    }

                    task.addOnFailureListener {

                    }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEjercicioPracticoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            2000
        ).setMaxUpdates(3).build()

        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                if (locationResult != null) {
                    locationResult.locations.forEach { location ->
                        currentLocation = location
                        Log.d(
                            "UCE",
                            "Ubicacion: ${location.latitude}, ${location.longitude}"
                        )
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        initClass()


    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()

        fusedLocationProviderClient.removeLocationUpdates(locationCallBack)
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

        //Diferencia con la primera es que las 2 se van a comunicar y cuando lo hagan se va alanzar este contrato
        binding.btnResult.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
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