package com.example.quakereport.screens

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.hardware.camera2.CameraManager
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.quakereport.R

class FlashLightActivity : AppCompatActivity() {

    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null
    private var isFlashlightOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_light)

        // Handling the back button to navigate back to SurvivalToolsContents
        val bckBut = findViewById<ImageView>(R.id.flashLightBackButton)
        bckBut.setOnClickListener {
            finish()
        }


        //FlashLight switch handling
        val flashlightSwitch = findViewById<Switch>(R.id.flashlightSwitch)
        flashlightSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                turnOnFlashlight()
            } else {
                turnOffFlashlight()
            }
        }

        //Camera Service
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = cameraManager!!.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun turnOnFlashlight() {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            try {
                cameraManager?.setTorchMode(cameraId!!, true)
                isFlashlightOn = true
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }

    private fun turnOffFlashlight() {
        if (isFlashlightOn) {
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
                isFlashlightOn = false
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }
    override fun onBackPressed() {
        finish()
    }
}
