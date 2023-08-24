package com.example.dispositivosmoviles.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityCameraBinding
import com.example.dispositivosmoviles.databinding.ActivityNotificationBinding
import com.example.dispositivosmoviles.ui.utilities.BradCasterNotifications
import java.util.Calendar

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        //Se debe ubicar en un lugar donde se cree una sola vez
        //Just once
        createNotificationChannel()

        setContentView(binding.root)

        binding.btnNotification.setOnClickListener {
            //createNotificationChannel()
            sendNotification()
        }
        binding.btnNotificationProgramada.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hora = binding.timePicker.hour
            val minutes = binding.timePicker.minute

            Toast.makeText(
                this,
                "La notificacion se activa a las: $hora con $minutes",
                Toast.LENGTH_LONG
            ).show()
            calendar.set(Calendar.HOUR, hora)
            calendar.set(Calendar.MINUTE, minutes)
            calendar.set(Calendar.SECOND, 0)

            sendNotificationTimePicker(calendar.timeInMillis)
        }
    }

    //Construccion externa del canal
    val CHANNEL: String = "Notificacion 1"

//Implementar el pending y flag

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal principal1"
            val descriptionText = "Canal de notificaciones de variedades"
            val importance = NotificationManager.IMPORTANCE_HIGH
            //(ID DEL CANAL,name,importance)
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    //Las echas se mandan como long

    @SuppressLint("ScheduleExactAlarm")
    private fun sendNotificationTimePicker(time: Long) {
        val myIntent = Intent(applicationContext, BradCasterNotifications::class.java)
        //No va a hacer que cuando este abierta actualice la aplicacion a la pantalla, y si es que no esta abierta que abra una nueva (Banderas)
        val myPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            myIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, myPendingIntent)
    }

    @SuppressLint("MissingPermission")
    fun sendNotification() {
        //Contruccion internar de la notificacion
        val intent = Intent(this, CameraActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        // Esta parte crea la notificacion
        val noti = NotificationCompat.Builder(this, CHANNEL)
            .setContentTitle("Primera notificacion")
            .setContentText("Tienes una notificacion") // Este texto se muestra cuando aparece la notificacion
            .setSmallIcon(R.drawable.baseline_chat_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Esta es una notificacion para recordar que estamos trabajando con Android")
            ) // Este texto se muestra cuando se abre el gestor de notificaciones

        // Aqui se envia la notificacion
        with(NotificationManagerCompat.from(this)) {
            notify(1, noti.build())
        }
    }

}