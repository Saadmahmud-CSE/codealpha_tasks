package com.saad.cse.randomquotegenerator

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }
}