package com.example.myat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class change_password : AppCompatActivity() {

    val bup = findViewById<Button>(R.id.btnupdatepasswrd)
    val ftup = findViewById<EditText>(R.id.frgtpasswrd)
    val ftup2 = findViewById<EditText>(R.id.frgtpasswrd2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        bup.setOnClickListener {
            Toast.makeText(this,"Password Updated.",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,log_in::class.java)
            startActivity(intent)
            finish()
        }
    }
}