package com.example.movieapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val appNameText = findViewById<TextView>(R.id.appNameText)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        appNameText.startAnimation(fadeIn)


        Handler().postDelayed({
            Log.d("ncr","the splash screen is launched")
            // After delay, start MainActivity
            val intent = Intent(this, MovieGenreActivity::class.java)
            startActivity(intent)
            finish()  // Finish SplashActivity so the user can't go back to it
        }, 3000)
    }
}