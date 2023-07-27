package com.example.myat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class splashscreen : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 1000 // 1 seconds
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        // Use a Handler to delay the transition to the next activity
        android.os.Handler().postDelayed({
            auth = FirebaseAuth.getInstance()

            // Check the user's authentication status
            val currentUser = auth.currentUser
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            Log.e("currentUser",currentUser?.email.toString())
            /*if (currentUser != null) {
                // User is authenticated, navigate to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            }else{*/
                // User is not authenticated, navigate to LoginActivity
                startActivity(Intent(this, log_in::class.java))

            //}
            // Close the splash activity to prevent the user from navigating back to it
            finish()
        }, SPLASH_DELAY)
    }
}
