package com.example.dispositivosmoviles.ui.utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.ui.activities.NotificationActivity

class BradCasterNotifications :BroadcastReceiver(){
    val CHANNEL: String = "Solicitudes"

    //parametros mandatorios
    //siempre debo tener un contexto
    override fun onReceive(context: Context, intent: Intent) {

        val myIntent=Intent(context,NotificationActivity::class.java)

        val myPendingIntent=PendingIntent.getBroadcast(
            context,
            0,
            myIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notif = NotificationCompat.Builder(context, CHANNEL)

        notif.setContentTitle("Primera notificacion mi llave :v")
        notif.setContentText("Abre tu wea :v")
        notif.setSmallIcon(com.google.android.material.R.drawable.abc_btn_colored_material)
        notif.setPriority(NotificationCompat.PRIORITY_HIGH)

        notif.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Esta es una notificacion en android que no se te olvide xd")
        )

        //crear un manager de tipo broadcast
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            1,
            notif.build()
        )

    }

}