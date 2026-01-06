package com.example.actividades_corrutinas_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.actividades_corrutinas_kotlin.activities.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.card_activity_1).setOnClickListener {
            startActivity(Intent(this, SequentialTasksActivity::class.java))
        }
        
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.card_activity_2).setOnClickListener {
            startActivity(Intent(this, TimerActivity::class.java))
        }
        
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.card_activity_3).setOnClickListener {
            startActivity(Intent(this, ApiSimulationActivity::class.java))
        }
        
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.card_activity_4).setOnClickListener {
            startActivity(Intent(this, WeatherForecastActivity::class.java))
        }
        
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.card_activity_5).setOnClickListener {
            startActivity(Intent(this, FileDownloaderActivity::class.java))
        }
        
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.card_activity_6).setOnClickListener {
            startActivity(Intent(this, NotificationSystemActivity::class.java))
        }
    }
}