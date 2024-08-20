package com.ziadahmed.logintask

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay for 2 seconds and then move to MoviesActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MoviesActivity::class.java))
            finish() // Close the SplashActivity so the user can't navigate back to it
        }, 2000) // 2000ms = 2 seconds
    }
}
