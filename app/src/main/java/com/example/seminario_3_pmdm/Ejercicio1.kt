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

class Ejercicio1 : AppCompatActivity() {

    val CHANNEL_ID = "generic_notification_kebab"
    lateinit var builder :  NotificationCompat.Builder

    fun createNotificactionChannel(name: String, desc: String){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "OSTIA TIO"
            val descriptionText = "QUE NO LO HE ENCHUFAO"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply{
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    fun createNotification(title : String, content : String, largeIcon: Int) :  NotificationCompat.Builder
    {

        val intent = Intent(this, MainActivity::class.java).apply {
            var flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setLargeIcon(getDrawable(largeIcon)!!.toBitmap())
            .setContentIntent(pendingIntent)



        return builder
    }

    fun sendNotification(){
        with(this.getSystemService<NotificationManager>()) {
            this?.notify(1, builder.build())
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejercicio_1)

        createNotificactionChannel("OSTIA TIO", "QUE NO LO HE ENCHUFAO")



        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            builder = createNotification("AYUDA", "POR FAVOR ME ESTAN MATANDO", R.drawable.ostia)
            sendNotification()
        }

    }
}