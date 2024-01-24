package com.example.seminario_3_pmdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)

        button.setOnClickListener {
            var intent = Intent(this, Ejercicio1::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            var intent = Intent(this, Ejercicio2::class.java)
            startActivity(intent)
        }


    }
}