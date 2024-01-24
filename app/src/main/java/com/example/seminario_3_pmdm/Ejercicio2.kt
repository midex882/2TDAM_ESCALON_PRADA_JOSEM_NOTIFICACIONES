package com.example.seminario_3_pmdm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.core.graphics.drawable.toBitmap

class Ejercicio2 : AppCompatActivity(){


    val CHANNEL_ID = "generic_notification_kebab"
    lateinit var builder :  NotificationCompat.Builder

    fun createNotificactionChannel(name: String, desc: String){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply{
                description = desc
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    fun createNotification(title : String, content : String, largeIcon: Int, mode: String) :  NotificationCompat.Builder
    {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        if(mode == "bigtext")
        {
            builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(getDrawable(largeIcon)!!.toBitmap())
                .setContentIntent(pendingIntent)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("OSTIA TIO QUE NO LO HE ENCHUFAO"))
        }else{
            builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(getDrawable(largeIcon)!!.toBitmap())
                .setContentIntent(pendingIntent)
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(getDrawable(R.drawable.ostia)!!.toBitmap())
                    .bigLargeIcon(null))
        }

        return builder
    }

    fun sendNotification(){
        with(this.getSystemService<NotificationManager>()) {
            this?.notify(1, builder.build())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejercicio_2)

        var button1 = findViewById<Button>(R.id.button)
        var button2 = findViewById<Button>(R.id.button2)
        builder = createNotification("AYUDA", "POR FAVOR ME ESTAN MATANDO", R.drawable.ostia, "bigtext")
        createNotificactionChannel("OSTIA TIO", "QUE NO LO HE ENCHUFAO")

        button1.setOnClickListener {
            builder = createNotification("OSTIA", "OSTIA TIO QUE NO LO HE ENCHUFAO lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem ", R.drawable.ostia, "bigtext")
            sendNotification()
        }

        button2.setOnClickListener {
            builder = createNotification("OSTIA", "OSTIA TIO UNA FOTO", R.drawable.ostia, "bigpicture")
            sendNotification()
        }


    }
}