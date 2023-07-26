package com.example.myat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class forgot_password:AppCompatActivity() {

    val edtxt = findViewById<EditText>(R.id.t_mail)
    val btn = findViewById<Button>(R.id.btnotp)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)

        btn.setOnClickListener {

        }
    }

}