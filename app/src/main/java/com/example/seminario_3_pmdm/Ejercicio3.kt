// Ejercicio3.kt

package com.example.seminario_3_pmdm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.core.graphics.drawable.toBitmap

class Ejercicio3 : AppCompatActivity() {

    lateinit var builder: NotificationCompat.Builder
    val CHANNEL_ID = "generic_notification_kebab"
    lateinit var title : EditText
    lateinit var description : EditText
    lateinit var planet: Spinner
    lateinit var icon : Spinner
    lateinit var number: EditText
    lateinit var names: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejercicio_3)



        title = findViewById<EditText>(R.id.editTextTitle)
        description = findViewById<EditText>(R.id.editTextDescription)
        planet = findViewById<Spinner>(R.id.spinnerPlanet)
        icon = findViewById<Spinner>(R.id.spinnerIcon)
        number = findViewById<EditText>(R.id.editTextNumber)
        names = findViewById<EditText>(R.id.editTextNames)

        val buttonSend = findViewById<Button>(R.id.buttonSend)

        createNotificactionChannel("OSTIA TIO", "QUE NO LO HE ENCHUFAO")
        buttonSend.setOnClickListener {
            builder = createNotification()
            sendNotification()
        }
    }

    fun createNotification() :  NotificationCompat.Builder
    {

        val titletext = title.text.toString()
        val descriptiontext = description.text.toString()
        val planeticon = planet.selectedItem.toString()
        val iconicon = icon.selectedItem.toString()
        val numbertext = number.text.toString().toIntOrNull() ?: 1
        val namestext = names.text.toString().split(",")

        // Assuming you have appropriate icons in your drawable folder for icon1, icon2, icon3
        val iconResource = when (iconicon) {
            "icon1" -> R.drawable.earth
            "icon2" -> R.drawable.venus
            "icon3" -> R.drawable.mars
            else -> R.drawable.mars
        }

        builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(titletext)
            .setContentText(descriptiontext)
            .setLargeIcon(getDrawable(iconResource)?.toBitmap())
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(getDrawable(iconResource)?.toBitmap())
                .bigLargeIcon(null))


        // Adding buttons based on names
        for (i in 0 until namestext.size) {
            val action = NotificationCompat.Action(
                R.drawable.ic_launcher_foreground,
                namestext[i].trim(),
                null
            )
            builder.addAction(action)
        }



        return builder
    }

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

    fun sendNotification(){
        with(this.getSystemService<NotificationManager>()) {
            this?.notify(1, builder.build())
        }
    }
}
