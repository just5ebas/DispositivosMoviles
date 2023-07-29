package com.example.dispositivosmoviles.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityCameraBinding
import com.example.dispositivosmoviles.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotification.setOnClickListener {
            // No es necesario crear el canal cada vez
            // Basta que se cree una primera vez
            createNotificationChannel()

            sendNotification()
        }
    }

    private val CHANNEL: String = "Notificacion"

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Variedades"
            val descriptionText = "Notificaciones simples de variedades"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Registrar el canal en el sistema
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun sendNotification() {
        // Esta parte crea la notificacion
        val noti = NotificationCompat.Builder(this, CHANNEL)
            .setContentTitle("Primera notificacion")
            .setContentText("Tienes una notificacion") // Este texto se muestra cuando aparece la notificacion
            .setSmallIcon(R.drawable.tune_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Esta es una notificacion para recordar que estamos trabajando con Android")) // Este texto se muestra cuando se abre el gestor de notificaciones

        // Aqui se envia la notificacion
        with(NotificationManagerCompat.from(this)){
            notify(1, noti.build())
        }
    }
}