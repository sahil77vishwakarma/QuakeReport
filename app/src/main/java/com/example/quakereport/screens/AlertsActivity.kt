package com.example.quakereport.screens

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.quakereport.R

class AlertsActivity : AppCompatActivity() {

    private var music: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts)

        music = MediaPlayer.create(this, R.raw.alert_sound)
        music?.start()

        // Handling the back button and stop functionality
        val bckBut = findViewById<ImageView>(R.id.alertBackButton)
        bckBut.setOnClickListener {
            finishAlertActivity()
        }
    }

    private fun finishAlertActivity() {
        music?.stop()
        finish()
    }

    override fun onBackPressed() {
        finishAlertActivity()
//        super.onBackPressed()
    }
}
