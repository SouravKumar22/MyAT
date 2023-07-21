package com.example.myat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class splashscreen : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 2000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        // Use a Handler to delay the transition to the next activity
        android.os.Handler().postDelayed({
            val intent = Intent(this, log_in::class.java)
            startActivity(intent)

            // Close the splash activity to prevent the user from navigating back to it
            finish()
        }, SPLASH_DELAY)
    }
}
